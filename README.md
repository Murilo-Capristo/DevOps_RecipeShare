# Projeto RecipeShare - Guia de Deploy e Execução na Azure

Este documento descreve o processo completo de provisionamento da infraestrutura na Microsoft Azure, deploy da aplicação Java (Spring Boot) via GitHub Actions e verificação da sua funcionalidade.

---

## 📝 Descrição da Solução

O projeto **RecipeShare** é uma aplicação web para gerenciamento de receitas culinárias, integrando funcionalidades de CRUD (criação, leitura, atualização e exclusão) e autenticação via GitHub OAuth. A aplicação utiliza banco de dados SQL Server na Azure e é implantada via App Service, permitindo acesso público através da web.

---

## 🎯 Sumário

- [Pré-requisitos](#-pré-requisitos)
- [Parte 1: Provisionamento da Infraestrutura do Banco de Dados](#-parte-1-provisionamento-da-infraestrutura-do-banco-de-dados)
- [Parte 2: Deploy da Aplicação com Script Automatizado](#-parte-2-deploy-da-aplicação-com-script-automatizado)
- [Parte 3: Configuração do Deploy Contínuo com GitHub Actions](#-parte-3-configuração-do-deploy-contínuo-com-github-actions)
  - [3.1 Configurando os Secrets do Repositório](#31-configurando-os-secrets-do-repositório)
  - [3.2 Ajustando o Workflow](#32-ajustando-o-workflow)
- [Parte 4: Verificação e Testes](#-parte-4-verificação-e-testes)
- [Considerações Finais](#-considerações-finais)

---

## ✔️ Pré-requisitos

Antes de começar, garanta que você tenha:

1. Conta na Microsoft Azure com assinatura ativa.
2. Azure CLI instalada ou acesso ao Cloud Shell pelo portal Azure.
3. Repositório GitHub com o código-fonte da aplicação.
4. Segredos do GitHub OAuth configurados (client_id e client_secret).

---

## 🚀 Parte 1: Provisionamento da Infraestrutura do Banco de Dados

Criação do grupo de recursos, servidor SQL e banco de dados via script no Azure Cloud Shell.

```bash
#!/bin/bash

# Variáveis de configuração
RG="rg-recipeshare"
LOCATION="brazilsouth"
SERVER_NAME="sqlserver-rm556794"
USERNAME="admsql"
PASSWORD="Fiap@2tdsvms"
DBNAME="recipesharedb"

# Criar grupo de recursos
az group create --name $RG --location $LOCATION

# Criar servidor SQL
az sql server create -l $LOCATION -g $RG -n $SERVER_NAME -u $USERNAME -p $PASSWORD --enable-public-network true

# Criar banco de dados vazio
az sql db create -g $RG -s $SERVER_NAME -n $DBNAME --service-objective Basic --backup-storage-redundancy Local --zone-redundant false

# Configurar regra de firewall
az sql server firewall-rule create -g $RG -s $SERVER_NAME -n AllowAll --start-ip-address 0.0.0.0 --end-ip-address 255.255.255.255

echo "Infraestrutura do banco criada com sucesso!"
```

---

## ⚙️ Parte 2: Deploy da Aplicação com Script Automatizado

```bash
#!/bin/bash

# Variáveis da aplicação
export RESOURCE_GROUP_NAME="rg-recipeshare"
export WEBAPP_NAME="recipeshare-rm556794"
export APP_SERVICE_PLAN="planRecipeshare"
export LOCATION="brazilsouth"
export RUNTIME="JAVA:17-java17"
export GITHUB_REPO_NAME="Murilo-Capristo/DevOps_RecipeShare"
export BRANCH="main"
export APP_INSIGHTS_NAME="ai-recipeshare"

# Variáveis do banco de dados
export DB_SERVER_NAME="sqlserver-rm556794"
export DB_NAME="recipesharedb"
export DB_USER="admsql"
export DB_PASSWORD="Fiap@2tdsvms"

# Variáveis GitHub OAuth
export RSGITHUB_CLIENT_ID="seu_client_id"
export RSGITHUB_CLIENT_SECRET="seu_client_secret"

# JDBC URL
export JDBC_URL="jdbc:sqlserver://${DB_SERVER_NAME}.database.windows.net:1433;database=${DB_NAME};encrypt=true;trustServerCertificate=false;hostNameInCertificate=*.database.windows.net;loginTimeout=30;"

# Criar Application Insights
az monitor app-insights component create --app "$APP_INSIGHTS_NAME" --location "$LOCATION" --resource-group "$RESOURCE_GROUP_NAME" --application-type web

# Criar App Service Plan
az appservice plan create --name "$APP_SERVICE_PLAN" --resource-group "$RESOURCE_GROUP_NAME" --location "$LOCATION" --sku F1 --is-linux

# Criar Web App
az webapp create --name "$WEBAPP_NAME" --resource-group "$RESOURCE_GROUP_NAME" --plan "$APP_SERVICE_PLAN" --runtime "$RUNTIME"

# Habilitar deploy
az resource update --resource-group "$RESOURCE_GROUP_NAME" --namespace Microsoft.Web --resource-type basicPublishingCredentialsPolicies --name scm --parent sites/"$WEBAPP_NAME" --set properties.allow=true

# Configurar variáveis de ambiente
CONNECTION_STRING=$(az monitor app-insights component show --app "$APP_INSIGHTS_NAME" --resource-group "$RESOURCE_GROUP_NAME" --query connectionString --output tsv)

az webapp config appsettings set --name "$WEBAPP_NAME" --resource-group "$RESOURCE_GROUP_NAME" --settings \
APPLICATIONINSIGHTS_CONNECTION_STRING="$CONNECTION_STRING" \
ApplicationInsightsAgent_EXTENSION_VERSION="~3" \
XDT_MicrosoftApplicationInsights_Mode="Recommended" \
XDT_MicrosoftApplicationInsights_PreemptSdk="1" \
SPRING_DATASOURCE_USERNAME="$DB_USER" \
SPRING_DATASOURCE_PASSWORD="$DB_PASSWORD" \
SPRING_DATASOURCE_URL="$JDBC_URL" \
RSGITHUB_CLIENT_ID="$RSGITHUB_CLIENT_ID" \
RSGITHUB_CLIENT_SECRET="$RSGITHUB_CLIENT_SECRET"

# Reiniciar Web App
az webapp restart --name "$WEBAPP_NAME" --resource-group "$RESOURCE_GROUP_NAME"

# Conectar Application Insights
az monitor app-insights component connect-webapp --app "$APP_INSIGHTS_NAME" --web-app "$WEBAPP_NAME" --resource-group "$RESOURCE_GROUP_NAME"

# Configurar GitHub Actions
az webapp deployment github-actions add --name "$WEBAPP_NAME" --resource-group "$RESOURCE_GROUP_NAME" --repo "$GITHUB_REPO_NAME" --branch "$BRANCH" --login-with-github

echo "Deploy concluído com sucesso!"
```

---

## 🔧 Parte 3: Configuração do Deploy Contínuo com GitHub Actions

### 3.1 Configurando os Secrets do Repositório

- `SPRING_DATASOURCE_USERNAME`: admsql
- `SPRING_DATASOURCE_PASSWORD`: Fiap@2tdsvms
- `SPRING_DATASOURCE_URL`: JDBC URL do banco
- `RSGITHUB_CLIENT_ID`: GitHub OAuth Client ID
- `RSGITHUB_CLIENT_SECRET`: GitHub OAuth Client Secret
- `AzureAppService_PublishProfile_...`: conteúdo do arquivo de publicação do App Service

### 3.2 Workflow de Build e Deploy

```yaml
name: 'Build and deploy JAR app to Azure Web App: recipeshare-rm556794'

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build-and-deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v2
      - name: Set up Java version
        uses: actions/setup-java@v1
        with:
          java-version: '17'
      - name: Grant execute permission to gradlew
        run: chmod +x ./gradlew
      - name: Build with Gradle
        env:
          SPRING_DATASOURCE_URL: ${{ secrets.SPRING_DATASOURCE_URL }}
          SPRING_DATASOURCE_USERNAME: ${{ secrets.SPRING_DATASOURCE_USERNAME }}
          SPRING_DATASOURCE_PASSWORD: ${{ secrets.SPRING_DATASOURCE_PASSWORD }}
          RSGITHUB_CLIENT_ID: ${{ secrets.RSGITHUB_CLIENT_ID }}
          RSGITHUB_CLIENT_SECRET: ${{ secrets.RSGITHUB_CLIENT_SECRET }}
        run: ./gradlew clean build --stacktrace
      - name: Deploy to Azure Web App
        uses: azure/webapps-deploy@v2
        with:
          app-name: 'recipeshare-rm556794'
          slot-name: 'production'
          publish-profile: ${{ secrets.AzureAppService_PublishProfile_9b1782ca50874fb7906cce20af2bea5e }}
          package: 'build/libs/*-SNAPSHOT.jar'
```

---

## 🔬 Parte 4: Verificação e Testes

- Acesse o Web App no Azure para verificar a aplicação rodando.
- Use o Swagger ou Postman para testar os endpoints CRUD.
- Certifique-se que as tabelas do banco foram criadas pelo Flyway.

---

## 💡 Considerações Finais

- Garanta que o Flyway tenha suas migrations corretas.
- Verifique logs do App Service em caso de erro.
- Confirme todas as variáveis de ambiente e secrets configurados corretamente.

---

## 🧑‍💻 Integrantes do Grupo

- Murilo Capristo - RM556794
- Guilherme Romanholi - RM557462
- Nicolas Guinante - RM557844


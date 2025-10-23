#!/bin/bash

# Parar execução em caso de erro
set -e

REPO_URL="https://github.com/cyberbeef-projeto/looca-api-monitoramento.git"
APP_DIR="meu-app/looca-api-prod/looca-api-prod"
JAR_DIR="target"

echo "=== Atualizando pacotes do sistema ==="
sudo apt update -y && sudo apt upgrade -y

echo "=== Instalando Java 21 (OpenJDK) ==="
sudo apt install -y openjdk-21-jdk

echo "=== Verificando versão do Java ==="
java -version

echo "=== Instalando Maven ==="
sudo apt install -y maven

echo "=== Verificando versão do Maven ==="
mvn -version

if [ -d "$APP_DIR" ]; then
    echo "=== Diretório '$APP_DIR' já existe, atualizando com git pull ==="
    cd "$APP_DIR"
    git pull
else
    echo "=== Clonando repositório '$REPO_URL' ==="
    git clone "$REPO_URL" "$APP_DIR"
    cd "$APP_DIR"
fi

echo "=== Empacotando o projeto com Maven ==="
mvn clean package -DskipTests


echo "=== Localizando JAR gerado ==="
JAR_FILE=$(find "$JAR_DIR" -type f -name "*.jar" | head -n 1)

if [ -z "$JAR_FILE" ]; then
    echo "❌ Nenhum arquivo JAR encontrado em '$JAR_DIR/'"
    exit 1
fi

echo "✅ JAR encontrado: $JAR_FILE"
echo "=== Executando aplicação ==="

# Executa o JAR
java -jar "$JAR_FILE"

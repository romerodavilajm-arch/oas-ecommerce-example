# Base: Java 25 JDK
FROM eclipse-temurin:25-jdk

# Instalar Maven 3.9.9 desde Apache
ARG MAVEN_VERSION=3.9.9
ARG MAVEN_HOME=/opt/maven
ENV MAVEN_HOME=${MAVEN_HOME}
ENV PATH=${MAVEN_HOME}/bin:${PATH}

RUN apt-get update && \
    apt-get install -y wget && \
    wget -q https://archive.apache.org/dist/maven/maven-3/${MAVEN_VERSION}/binaries/apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    tar -xzf apache-maven-${MAVEN_VERSION}-bin.tar.gz -C /opt && \
    mv /opt/apache-maven-${MAVEN_VERSION} ${MAVEN_HOME} && \
    rm apache-maven-${MAVEN_VERSION}-bin.tar.gz && \
    apt-get remove -y wget && \
    apt-get autoremove -y && \
    rm -rf /var/lib/apt/lists/*

WORKDIR /app

# Copiar solo el pom.xml para precargar dependencias (capa cacheable)
COPY pom.xml .
RUN mvn dependency:go-offline

# El resto del código se montará como volumen para poder modificarlo en caliente
CMD ["tail", "-f", "/dev/null"]
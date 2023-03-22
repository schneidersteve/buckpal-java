FROM mcr.microsoft.com/devcontainers/base:bullseye

ARG GRAALVM_VERSION=graalvm-ce-java17-22.3.1
ARG INSTALL_DIR=/home/vscode

RUN apt update &&\
apt -y full-upgrade &&\
apt install build-essential libz-dev zlib1g-dev &&\
curl -sL https://get.graalvm.org/jdk | bash -s -- --to "$INSTALL_DIR" $GRAALVM_VERSION &&\
chown -R vscode $INSTALL_DIR/$GRAALVM_VERSION &&\
chgrp -R vscode $INSTALL_DIR/$GRAALVM_VERSION

ENV PATH=$INSTALL_DIR/$GRAALVM_VERSION/bin:$PATH
ENV JAVA_HOME=$INSTALL_DIR/$GRAALVM_VERSION

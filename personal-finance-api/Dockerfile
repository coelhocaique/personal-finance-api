FROM openjdk:11

LABEL source="https://github.com/coelhocaique/finance"\
      maintainer="Caique Coelho"

ADD ./build/distributions/*.zip /personal-finance-api.zip

RUN unzip personal-finance-api.zip && \
   rm -rf *.zip && \
   mv personal-finance-api-* personal-finance-api

EXPOSE 80 443

ENTRYPOINT [ "/personal-finance-api/bin/personal-finance-api" ]

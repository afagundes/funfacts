FROM flyway/flyway:9.21.1

ADD migrations/*.sql /flyway/sql

ENTRYPOINT ["flyway"]
CMD ["-?"]

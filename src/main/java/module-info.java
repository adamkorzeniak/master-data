module master.data {

    requires java.persistence;
    requires java.validation;

    requires spring.beans;
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.core;
    requires spring.data.jpa;
    requires spring.security.config;
    requires spring.security.core;
    requires spring.security.web;
    requires spring.web;
    requires spring.webmvc;

    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;

    requires org.aspectj.weaver;
    requires org.jboss.logging;

    requires static lombok;

}
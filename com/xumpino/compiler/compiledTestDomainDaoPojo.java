package com.xumpino.compiler.compiled; @javax.persistence.Entity @javax.persistence.Table(name="PERSOON") public class TestDomainDaoPojo implements com.mycompany.xumpico.example.domain.TestDomain { 
@javax.persistence.Id @javax.persistence.Column(name="PK_ID") private Integer pk_id; 
@javax.persistence.Column(name="VOORNAAM")  private java.lang.String voornaam; 
@javax.persistence.Column(name="NAAM")  private java.lang.String naam; 
public Integer getPkId(){ return this.pk_id; }
@Override public java.lang.String getVoornaam(){ return this.voornaam;} 
@Override public java.lang.String getNaam(){ return this.naam;} 
}
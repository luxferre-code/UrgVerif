# UrgVerif

---

## UrgVerif, c'est quoi ?

UrgVerif est une application web permettant de simplifier les vérifications des agents.  
Cette application web est conçu pour pouvoir être utilisé sur le téléphone des agents.  

## Installation

Pour installer UrgVerif sur votre serveur, il faut impérativement:  
- Tomcat  
- Une base de données SQL (PostGres, MySQL, H2, ...)  
- Les libraires:  
    - Commons Codec  
    - Commons Lang3  
    - Commons Text  
    - La lib de votre bdd  

Pour procéder à l'installation, rien de plus simple:  

```bash
cd $TOMCAT_URGVERIF_DIR
git clone https://github.com/luxferre-code/UrgVerif.git
cd urgverif/
./setup.sh
```  
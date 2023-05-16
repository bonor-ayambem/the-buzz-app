# Back-End Server


## Deploying
### To deploy to Heroku

```console
mvn package; mvn heroku:deploy
```

### To deploy to local
```console
bash deploy.sh
```
You can access the DB though **localhost:4567**


### VARS
| VARS      | VALUES |
| ----------- | ----------- |
| DATABASE_URL      | postgres://eehbofgeeadmjf:753fdc0ca74cecb3502d61746fe358ead0c31c0dd7f7d474f7d3ff7593b01ebc@ec2-3-219-111-26.compute-1.amazonaws.com:5432/dco1kb5belobn0       |
| CORS_ENABLED   | True        |

# Serenity JUnit Screenplay Starter project
*no cucumbers where harmed during development*
  
This is a minimalistic example using Serenity BDD with the screenplay patter but without Cucumber. It shows how actors with different abilities can work on different interfaces in isolation or in collaboration. 

## Get the code

Git:

    git git@github.com:globalworming/serenity-junit-screenplay-starter.git
    cd serenity-junit-screenplay-starter


## Use Maven to run all tests

Open a command window and run:

    mvn verify

## But there are some tests failing?!

Some tests depend on a local react app `npm --prefix ./src/react run start` and a local spring boot application `mvn spring-boot:run`. Others are just not maintained anymore. Others may require you to create accounts and API keys.


## Viewing the reports

Running `mvn verify` will produce a Serenity test report in the `target/site/serenity` directory. Go take a look!

# Need help?

Create an [Issue](https://github.com/globalworming/serenity-junit-screenplay-starter/issues) or join the [gitter chat](https://gitter.im/serenity-bdd/serenity-core)  

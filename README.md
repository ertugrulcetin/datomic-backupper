# Datomic Backupper

Backs up your Datomic Database regularly to your S3 bucket or your file system

## Usage(for AWS)

1. change core.clj conf map according to your information and set :local? to false
2. configure resources/logback.xml if you need logging(you can change your logging lib from project.clj)
3. copy all your Datomic files(bin, lib, config, datomic-pro.jar, config etc.) into deployment/datomic
4. lein uberjar
5. move datomic-backupper.jar to deployment folder
6. configure your AWS credentials in Dockerfile
7. zip everything in deployment directory(cd deployment -> zip datomic-backupper.zip -r datomic datomic-backuper.jar Dockerfile Dockerrun.aws.json)
8. upload zip to AWS(EC2 or Elastic Beanstalk, you need instance with at least 2GB of RAM)

## Usage(for Local Machine)

1. change core.clj conf map according to your information and set :local? to true
2. configure resources/logback.xml if you need logging(you can change your logging lib from project.clj)
3. copy all your Datomic files(bin, lib, config, datomic-pro.jar, config etc.) into deployment/datomic
4. lein run

## License

Copyright 2017 Ertuğrul Çetin

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

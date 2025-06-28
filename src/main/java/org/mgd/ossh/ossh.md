### 如何将自己的gradle开源项目发布到maven中央仓库（2024年3月最新版保姆级教程）
mvn gpg:sign-and-deploy-file -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=mvn-center -DpomFile=simple-statemachine-1.0-SNAPSHOT.pom -Dfile=simple-statemachine-1.0-SNAPSHOT.jar
mvn gpg:sign-and-deploy-file -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=mvn-center -DpomFile=simple-statemachine-1.0-SNAPSHOT.pom -Dfile=simple-statemachine-1.0-SNAPSHOT-sources.jar -Dclassifier=sources
mvn gpg:sign-and-deploy-file -Durl=https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/ -DrepositoryId=mvn-center -DpomFile=simple-statemachine-1.0-SNAPSHOT.pom -Dfile=simple-statemachine-1.0-SNAPSHOT-javadoc.jar -Dclassifier=javadoc


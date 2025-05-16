pipeline {
    agent any

    parameters {
        choice(name: 'BUILD_TYPE', choices: ['full', 'incremental'], description: 'Select build type')
    }
    environment{
        ARTIFACTORY_SERVER = 'artifactory-prod'
        ARTIFACTORY_REPO = 'scale-release-local'
        SBT_OPTS = "-Dsbt.log.noformat=true"
    }
    options{
        ansiColor('xterm')
        timestamps()
    }
    stages{
        stage('Checkout'){
            steps{
                checkout scm
            }
        }
        stage('Prepare'){
            steps{
                script{
                    if (params.BUILD_TYPE == 'incremental'){
                        def changedFiles = sh(script: 'git diff --name-only origin/main', returnStdout: true).trim()
                        echo "Changed files:\n${changedFiles}"
                        if (!changedFiles){
                            echo "no changes detected, skipping build"
                            currentBuild.result = 'SUCCESS'
                            return 
                        }
                    }
                }
            }
        }
        stage('Build & Test'){
            steps{
                sh './sbt clean ci-releaseEarly publishLocal'
            }
        }
        stage('Publish artifacts'){
            steps{
                script{
                    def server = Artifactory.server(ARTIFACTORY_SERVER)
                    def uploadSpec = """{
                      "files": [{
                        "pattern": "target/*.jar",
                        "target": "${ARTIFACTORY_REPO}"
                      }]
                    }"""
                    server.upload spec: uploadSpec
                    echo "Artifacts uploaded to Artifactory."
                }
            }
        }
    }
    post{
        success{
            echo "Build success"
        }
        failure{
            echo "build failed"
        }
    }
}
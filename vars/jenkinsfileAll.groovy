def call()
{

pipeline {
    agent none

    stages {
        stage('Read Properties') {
            agent any

            steps {
                script {
                     properties = readYaml file: "${WORKSPACE}/properties.yaml"
                     pipelineCfg = readYaml file: "sample.yaml" 
                }
            }
        }

        stage('Compile Application') {
            when { expression { pipelineCfg.COMPILE == 'true' && pipelineCfg.TECHNOLOGY == 'java' } }
            // agent {
            //     kubernetes {
            //         yamlFile "${properties.JAVA_MAVEN_SLAVE_YAML}"
            //     }
            // }

            steps {
                println("Compile")
            }    
        }

        stage('Code Quality Analysis') {
            when { equals expected: 'true', actual: pipelineCfg.CODE_QUALITY }
            // agent {
            //     kubernetes {
            //         yamlFile "${properties.SONAR_SCANNER_SLAVE_YAML}"
            //     }
            // }
            steps {
                println("CodeQuality")
            }  
        }

        stage('Package Application') {
            when { expression { pipelineCfg.PACKAGE == 'true' && pipelineCfg.TECHNOLOGY == 'java' } }
            // agent {
            //     kubernetes {
            //         yamlFile "${properties.JAVA_MAVEN_SLAVE_YAML}"
            //     }
            // }

            steps {
                println("Package")
            }  
        }        

    }
}
}

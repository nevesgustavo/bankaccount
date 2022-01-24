node{

 stage('Git Checkout'){
	git 'https://github.com/javahometech/my-app'
 }
 stage('Maven Package'){
	sh 'mvn clean package'
	sh 'mv target/bankaccount*.war target/bankaccount.war'
 }

 stage('Build Docker Imager'){
   sh 'docker build -t gustavon/bankaccount:0.0.1 .'
 }

 stage('Push to Docker Hub'){

	 withCredentials([string(credentialsId: 'gustavon', variable: 'dockerHubPwd')]) {
        sh "docker login -u gustavon -p ${dockerHubPwd}"
     }
	 sh 'docker push gustavon/bankaccount:0.0.1'
 }
}
package com.github.danielflower.webtail;

import org.apache.commons.collections.buffer.CircularFifoBuffer;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LogInstance {
	private final String name;
	private final CircularFifoBuffer buffer = new CircularFifoBuffer(500);
	private LogListener logListener;
	private final AtomicInteger lines = new AtomicInteger();

	public LogInstance(String name) {
		this.name = name;

		Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Random random = new Random();
				while (true) {
					try {
						addLog(SAMPLE[Math.abs(random.nextInt(SAMPLE.length))]);
//						Thread.sleep(random.nextInt(1000) + 100);
						Thread.sleep(10);
					} catch (InterruptedException e) {
						break;
					}
				}
			}
		});
		thread.start();
	}

	public void addLog(String message) {
		LogLine logLine = new LogLine(message, lines.incrementAndGet());
		if (logListener != null) {
			logListener.onLog(this, logLine);
		}
		buffer.add(logLine);
	}

	public String getName() {
		return name;
	}

	public void setLogListener(LogListener logListener) {
		this.logListener = logListener;
	}

	public static class LogLine {
		private final int number;
		private final String value;

		public LogLine(String value, int number) {
			this.value = value;
			this.number = number;
		}

		public int getNumber() {
			return number;
		}

		public String getValue() {
			return value;
		}
	}


	private static final String[] SAMPLE = new String[]{
			"[INFO] ------------------------------------------------------------------------",
			"[INFO] Building ActiveMQ :: AMQP 5.10-SNAPSHOT",
			"[INFO] ------------------------------------------------------------------------",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.pom (2 KB at 40.3 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-j/0.6/proton-j-0.6.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-j/0.6/proton-j-0.6.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-j/0.6/proton-j-0.6.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-j/0.6/proton-j-0.6.pom (2 KB at 63.0 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-project/0.6/proton-project-0.6.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-project/0.6/proton-project-0.6.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-project/0.6/proton-project-0.6.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-project/0.6/proton-project-0.6.pom (4 KB at 88.7 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.pom (3 KB at 83.0 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-api/0.6/proton-api-0.6.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-api/0.6/proton-api-0.6.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-api/0.6/proton-api-0.6.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-api/0.6/proton-api-0.6.pom (2 KB at 46.8 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.pom (2 KB at 51.6 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.pom (896 B at 30.2 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.pom",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.pom",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.pom",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.pom (2 KB at 21.2 KB/sec)",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.jar",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.jar",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/proton-api/0.6/proton-api-0.6.jar",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.jar",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.jar",
			"Downloading: https://repo.eclipse.org/content/groups/releases/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.jar",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.jar",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.jar",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/proton-api/0.6/proton-api-0.6.jar",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.jar",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.jar",
			"Downloading: https://repository.jboss.org/nexus/content/repositories/fs-releases/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.jar",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.jar",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.jar",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-api/0.6/proton-api-0.6.jar",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.jar",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.jar",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-jms/0.6/proton-jms-0.6.jar (28 KB at 608.4 KB/sec)",
			"Downloading: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.jar",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-api/0.6/proton-api-0.6.jar (125 KB at 1218.2 KB/sec)",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client-jms/0.26/qpid-amqp-1-0-client-jms-0.26.jar (132 KB at 1203.8 KB/sec)",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-common/0.26/qpid-amqp-1-0-common-0.26.jar (531 KB at 2346.1 KB/sec)",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/proton-j-impl/0.6/proton-j-impl-0.6.jar (503 KB at 1904.2 KB/sec)",
			"Downloaded: http://repo.maven.apache.org/maven2/org/apache/qpid/qpid-amqp-1-0-client/0.26/qpid-amqp-1-0-client-0.26.jar (47 KB at 161.0 KB/sec)",
			"[INFO] ",
			"[INFO] --- maven-clean-plugin:2.5:clean (default-clean) @ activemq-amqp ---",
			"[INFO] ",
			"[INFO] --- maven-enforcer-plugin:1.0.1:enforce (default) @ activemq-amqp ---",
			"[INFO] ",
			"[INFO] --- maven-consolets-plugin:1.30:install (default) @ activemq-amqp ---",
			"[INFO] ",
			"[INFO] --- maven-bundle-plugin:2.3.7:cleanVersions (cleanVersions) @ activemq-amqp ---",
			"[INFO] ",
			"[INFO] --- maven-remote-resources-plugin:1.3:process (default) @ activemq-amqp ---",
			"[INFO] ",
			"[INFO] --- maven-resources-plugin:2.5:resources (default-resources) @ activemq-amqp ---",
			"[07:10:01] [debug] execute contextualize",
			"[INFO] Using 'UTF-8' encoding to copy filtered resources.",
			"[INFO] Copying 5 resources",
			"[INFO] Copying 3 resources",
			"[INFO] ",
			"[INFO] --- maven-compiler-plugin:2.5.1:compile (default-compile) @ activemq-amqp ---",
			"[INFO] Compiling 19 source files to /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/target/classes",
			"[WARNING] /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/src/main/java/org/apache/activemq/transport/amqp/AmqpProtocolConverter.java:[225,43] [deprecation] input(byte[],int,int) in org.apache.qpid.proton.engine.Transport has been deprecated",
			"[WARNING] /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/src/main/java/org/apache/activemq/transport/amqp/AmqpProtocolConverter.java:[633,30] [deprecation] MessageImpl() in org.apache.qpid.proton.message.impl.MessageImpl has been deprecated",
			"[WARNING] /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/src/main/java/org/apache/activemq/transport/amqp/ActiveMQJMSVendor.java:[84,23] [deprecation] createDestination(java.lang.String) in org.apache.qpid.proton.jms.JMSVendor has been deprecated",
			"[INFO] ",
			"[INFO] --- maven-resources-plugin:2.5:testResources (default-testResources) @ activemq-amqp ---",
			"[07:10:02] [debug] execute contextualize",
			"[INFO] Using 'UTF-8' encoding to copy filtered resources.",
			"[INFO] Copying 8 resources",
			"[INFO] Copying 3 resources",
			"[INFO] ",
			"[INFO] --- maven-compiler-plugin:2.5.1:testCompile (default-testCompile) @ activemq-amqp ---",
			"[INFO] Compiling 25 source files to /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/target/test-classes",
			"[INFO] ",
			"[INFO] --- maven-surefire-plugin:2.16:test (default-test) @ activemq-amqp ---",
			"[INFO] Surefire report directory: /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/target/surefire-reports",
			"[07:10:02] ",
			"[07:10:02] -------------------------------------------------------",
			"[07:10:02]  T E S T S",
			"[07:10:02] -------------------------------------------------------",
			"[07:10:02] Running org.apache.activemq.transport.amqp.JMSClientTest",
			"[07:12:20] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 137.846 sec - in org.apache.activemq.transport.amqp.JMSClientTest",
			"[07:12:20] Running org.apache.activemq.transport.amqp.joram.JoramJmsNioTest",
			"[07:18:58] Tests run: 187, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 398.152 sec - in org.apache.activemq.transport.amqp.joram.JoramJmsNioTest",
			"[07:18:58] Running org.apache.activemq.transport.amqp.joram.JoramJmsTest",
			"[07:25:23] Tests run: 181, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 385.015 sec - in org.apache.activemq.transport.amqp.joram.JoramJmsTest",
			"[07:25:23] Running org.apache.activemq.transport.amqp.joram.JoramJmsNioPlusSslTest",
			"[07:27:33] Tests run: 181, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 129.932 sec - in org.apache.activemq.transport.amqp.joram.JoramJmsNioPlusSslTest",
			"[07:27:33] Running org.apache.activemq.transport.amqp.joram.JoramSslTest",
			"[07:29:49] Tests run: 187, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 135.48 sec - in org.apache.activemq.transport.amqp.joram.JoramSslTest",
			"[07:29:49] Running org.apache.activemq.transport.amqp.JMSClientNioTest",
			"[07:32:40] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 171.018 sec - in org.apache.activemq.transport.amqp.JMSClientNioTest",
			"[07:32:40] Running org.apache.activemq.transport.amqp.AmqpTransformerTest",
			"[07:32:42] Tests run: 3, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 1.892 sec - in org.apache.activemq.transport.amqp.AmqpTransformerTest",
			"[07:32:42] Running org.apache.activemq.transport.amqp.JMSClientSslTest",
			"[07:35:00] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 138.588 sec - in org.apache.activemq.transport.amqp.JMSClientSslTest",
			"[07:35:00] Running org.apache.activemq.transport.amqp.bugs.AMQ4753Test",
			"[07:35:04] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.209 sec - in org.apache.activemq.transport.amqp.bugs.AMQ4753Test",
			"[07:35:04] Running org.apache.activemq.transport.amqp.bugs.AMQ4914Test",
			"[07:40:20] Tests run: 3, Failures: 0, Errors: 1, Skipped: 0, Time elapsed: 316.096 sec <<< FAILURE! - in org.apache.activemq.transport.amqp.bugs.AMQ4914Test",
			"[07:40:20] testSendHugeMessage(org.apache.activemq.transport.amqp.bugs.AMQ4914Test)  Time elapsed: 303.604 sec  <<< ERROR!",
			"[07:40:20] java.lang.Exception: test timed out after 300000 milliseconds",
			"[07:40:20] 	at java.lang.Object.wait(Native Method)",
			"[07:40:20] 	at java.lang.Object.wait(Object.java:485)",
			"[07:40:20] 	at org.apache.qpid.amqp_1_0.client.Receiver.receiveFromPrefetch(Receiver.java:328)",
			"[07:40:20] 	at org.apache.qpid.amqp_1_0.client.Receiver.receive(Receiver.java:258)",
			"[07:40:20] 	at org.apache.qpid.amqp_1_0.jms.impl.MessageConsumerImpl.receive0(MessageConsumerImpl.java:291)",
			"[07:40:20] 	at org.apache.qpid.amqp_1_0.jms.impl.MessageConsumerImpl.receiveImpl(MessageConsumerImpl.java:260)",
			"[07:40:20] 	at org.apache.qpid.amqp_1_0.jms.impl.MessageConsumerImpl.receive(MessageConsumerImpl.java:235)",
			"[07:40:20] 	at org.apache.qpid.amqp_1_0.jms.impl.MessageConsumerImpl.receive(MessageConsumerImpl.java:57)",
			"[07:40:20] 	at org.apache.activemq.transport.amqp.bugs.AMQ4914Test.doTestSendLargeMessage(AMQ4914Test.java:104)",
			"[07:40:20] 	at org.apache.activemq.transport.amqp.bugs.AMQ4914Test.testSendHugeMessage(AMQ4914Test.java:80)",
			"[07:40:20] ",
			"[07:40:20] Running org.apache.activemq.transport.amqp.AMQ4696Test",
			"[07:40:23] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.214 sec - in org.apache.activemq.transport.amqp.AMQ4696Test",
			"[07:40:23] Running org.apache.activemq.transport.amqp.SimpleAMQPAuthTest",
			"[07:40:30] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.802 sec - in org.apache.activemq.transport.amqp.SimpleAMQPAuthTest",
			"[07:40:30] Running org.apache.activemq.transport.amqp.JmsClientRequestResponseTest",
			"[07:40:36] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 6.368 sec - in org.apache.activemq.transport.amqp.JmsClientRequestResponseTest",
			"[07:40:36] Running org.apache.activemq.transport.amqp.AMQ4563Test",
			"[07:41:40] Tests run: 4, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 63.849 sec - in org.apache.activemq.transport.amqp.AMQ4563Test",
			"[07:41:40] Running org.apache.activemq.transport.amqp.JMSClientNioPlusSslTest",
			"[07:44:03] Tests run: 23, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 143.603 sec - in org.apache.activemq.transport.amqp.JMSClientNioPlusSslTest",
			"[07:44:03] Running org.apache.activemq.transport.amqp.AMQ4920Test",
			"[07:44:17] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 13.193 sec - in org.apache.activemq.transport.amqp.AMQ4920Test",
			"[07:44:17] ",
			"[07:44:17] Results :",
			"[07:44:17] ",
			"[07:44:17] Tests in error: ",
			"[07:44:17]   AMQ4914Test.testSendHugeMessage:80->doTestSendLargeMessage:104->Object.wait:485->Object.wait:-2 ? ",
			"[07:44:17] ",
			"[07:44:17] Tests run: 847, Failures: 0, Errors: 1, Skipped: 0",
			"[07:44:17] ",
			"[ERROR] There are test failures.",
			"",
			"Please refer to /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/target/surefire-reports for the individual test results.",
			"[JENKINS] Recording test results",
			"[INFO] ",
			"[INFO] --- maven-jar-plugin:2.4:jar (default-jar) @ activemq-amqp ---",
			"[INFO] Building jar: /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/target/activemq-amqp-5.10-SNAPSHOT.jar",
			"[INFO] ",
			"[INFO] --- maven-site-plugin:3.1:attach-descriptor (attach-descriptor) @ activemq-amqp ---",
			"[INFO] ",
			"[INFO] --- ianal-maven-plugin:1.0-alpha-1:verify-legal-files (default) @ activemq-amqp ---",
			"[INFO] Checking legal files in: activemq-amqp-5.10-SNAPSHOT.jar",
			"[INFO] ",
			"[INFO] --- maven-install-plugin:2.3.1:install (default-install) @ activemq-amqp ---",
			"[INFO] Installing /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/target/activemq-amqp-5.10-SNAPSHOT.jar to /home/jenkins/jenkins-slave/maven-repositories/0/org/apache/activemq/activemq-amqp/5.10-SNAPSHOT/activemq-amqp-5.10-SNAPSHOT.jar",
			"[INFO] Installing /home/jenkins/jenkins-slave/workspace/ActiveMQ/activemq-amqp/pom.xml to /home/jenkins/jenkins-slave/maven-repositories/0/org/apache/activemq/activemq-amqp/5.10-SNAPSHOT/activemq-amqp-5.10-SNAPSHOT.pom"
	};


}

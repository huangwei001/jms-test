package com.test.jms.queue;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.transport.stomp.Stomp;

import javax.jms.*;

public class AppProducer {


    private final static String url = "tcp://127.0.0.1:61616";

    private final static String queueName = "queue-test";

    public static void main(String[] args) throws JMSException {
        //1.创建连接工厂
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(url);
        //2.创建连接
        Connection connection = connectionFactory.createConnection();
        //3.打开连接
        connection.start();
        //4.创建回话
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //5.创建目标
        Destination destination = session.createQueue(queueName);
        //6.创建一个生产者
        MessageProducer producer = session.createProducer(destination);

        for (int i = 0; i < 100; i++) {
            TextMessage textMessage = session.createTextMessage("producer:" + i);
            System.out.println("发送目标:" + textMessage.getText());
            producer.send(textMessage);
        }
        //8.关闭连接
        connection.close();
    }

}

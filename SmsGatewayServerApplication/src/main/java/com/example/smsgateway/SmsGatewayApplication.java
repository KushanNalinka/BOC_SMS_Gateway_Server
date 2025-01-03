
//package com.example.smsgateway;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//@SpringBootApplication
//public class SmsGatewayApplication {
//	public static void main(String[] args) throws Exception {
//		try (ServerSocket serverSocket = new ServerSocket(8082)) {
//			System.out.println("Gateway Server running on port 8082...");
//			while (true) {
//				Socket clientSocket = serverSocket.accept();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//				String message;
//				while ((message = reader.readLine()) != null) {
//					System.out.println("Received an Email from SMS Receiving Server: " + message);
//				}
//			}
//		}
//	}
//}

//package com.example.smsgateway;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
//public class SmsGatewayApplication {
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SmsGatewayApplication.class, args);
//		try (ServerSocket serverSocket = new ServerSocket(8082)) {
//			System.out.println("Gateway Server running on port 8082...");
//			while (true) {
//				Socket clientSocket = serverSocket.accept();
//				BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//				OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
//				String message;
//				while ((message = reader.readLine()) != null) {
//					System.out.println("Received an Email from SMS Receiving Server: " + message);
//
//					// Generate a reference number (unique identifier)
//					String referenceNumber = "REF" + System.currentTimeMillis();
//					System.out.println("Generated Reference Number: " + referenceNumber);
//
//					// Send reference number back to SMS Receiving Server
//					writer.write(referenceNumber + "\n");
//					writer.flush();
//				}
//				clientSocket.close();
//			}
//		}
//	}
//}
//package com.example.smsgateway;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
//public class SmsGatewayApplication {
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SmsGatewayApplication.class, args);
//
//		// Run the ServerSocket on a separate thread to avoid blocking the main thread
//		new Thread(() -> {
//			try (ServerSocket serverSocket = new ServerSocket(8082)) {
//				System.out.println("Gateway Server running on port 8082...");
//				while (true) {
//					Socket clientSocket = serverSocket.accept();
//					BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//					OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream());
//					String message;
//					while ((message = reader.readLine()) != null) {
//						System.out.println("Received an Email from SMS Receiving Server: " + message);
//
//						// Generate a reference number (unique identifier)
//						String referenceNumber = "REF" + System.currentTimeMillis();
//						System.out.println("Generated Reference Number: " + referenceNumber);
//
//
//						//LogFile.info("Err |            |                 |" + message+ referenceNumber);
//
//						// Send reference number back to SMS Receiving Server
//						writer.write(referenceNumber + "\n");
//						writer.flush();
//					}
//					clientSocket.close();
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}).start();
//	}
//}

//package com.example.smsgateway;
//
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.OutputStreamWriter;
//import java.net.ServerSocket;
//import java.net.Socket;
//
//@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
//public class SmsGatewayApplication {
//
//	public static void main(String[] args) throws Exception {
//		SpringApplication.run(SmsGatewayApplication.class, args);
//
//		try (ServerSocket serverSocket = new ServerSocket(8082)) {
//			System.out.println("Gateway Server running on port 8082...");
//			while (true) {
//				Socket clientSocket = serverSocket.accept();
//				try (
//						BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
//						OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream())
//				) {
//					String message;
//					while ((message = reader.readLine()) != null) {
//						System.out.println("Received an Email from SMS Receiving Server: " + message);
//
//						// Generate a reference number (unique identifier)
//						String referenceNumber = "REF" + System.currentTimeMillis();
//						System.out.println("Generated Reference Number: " + referenceNumber);
//
//						// Send reference number back to SMS Receiving Server
//						writer.write(referenceNumber + "\n");
//						writer.flush();
//					}
//				} catch (Exception e) {
//					System.err.println("Error while processing client request: " + e.getMessage());
//					e.printStackTrace();
//				}
//			}
//		} catch (Exception e) {
//			System.err.println("Error starting the Gateway Server: " + e.getMessage());
//			e.printStackTrace();
//		}
//	}
//}

package com.example.smsgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;

@SpringBootApplication(exclude = {org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration.class})
public class SmsGatewayApplication {
	private static final Queue<String> recordQueue = new LinkedList<>(); // Queue to hold the records for processing

	public static void main(String[] args) throws Exception {
		SpringApplication.run(SmsGatewayApplication.class, args);

		try (ServerSocket serverSocket = new ServerSocket(8082)) {
			System.out.println("Gateway Server running on port 8082...");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				try (
						BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
						OutputStreamWriter writer = new OutputStreamWriter(clientSocket.getOutputStream())
				) {
					String message;
					while ((message = reader.readLine()) != null) {
						// Log received message
						LogFile.info("Received from SMS Receiving Server: " + message);

						// Add the message to the queue for processing
						recordQueue.add(message);

						// Process the record one by one
						while (!recordQueue.isEmpty()) {
							String record = recordQueue.poll(); // Get the record from the queue
							String referenceNumber = "REF" + System.currentTimeMillis();
							System.out.println("Generated Reference Number: " + referenceNumber);

							// Log the reference number generation
							LogFile.info("Generated Reference Number: " + referenceNumber + " for message: " + record);

							// Send the reference number back to the receiving server
							writer.write(referenceNumber + "\n");
							writer.flush();
						}
					}
				} catch (Exception e) {
					System.err.println("Error while processing client request: " + e.getMessage());
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.err.println("Error starting the Gateway Server: " + e.getMessage());
			e.printStackTrace();
		}
	}
}

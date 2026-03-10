# Multithreaded Data Processing System

This repository contains the implementation of a Multithreaded Data Processing system in Java. The project is designed to simulate a highly concurrent environment where multiple worker threads process data batches while safely sharing resources. 

The primary goal of the system is to demonstrate thread management, asynchronous task execution, and synchronization mechanisms to prevent race conditions and data corruption.

## System Architecture

The software architecture is modeled using the C4 Model. The repository includes the following structural diagrams:

* **Context Diagram (`context.png`)**: Illustrates the high-level interactions between the System Administrator, the OS File System, the Log Console, and the core processing system.
* **Container Diagram (`container.png`)**: Breaks down the architecture into primary containers, including the Configuration Application, the Core Multithreaded Engine, and the Shared Memory Buffer.
* **Component Diagram (`component.png`)**: Zooms into the Core Engine to display the internal components, such as the worker threads and resource controllers.

## Core Components and Workflow

The system relies on two main Java components to handle concurrency:

* **`SharedResource.java` (Shared Memory Buffer)**
  This class represents the critical section of the application. It utilizes a `ReentrantLock` to act as a Mutex, ensuring that when multiple threads attempt to process data simultaneously, only one thread can mutate the shared state at a time. It also uses `AtomicInteger` to safely track the number of processed batches across all active threads.

* **`lab4_ai.java` (Main Execution Engine)**
  This is the entry point of the application. It utilizes Java's `ExecutorService` to create a fixed thread pool. The system dispatches multiple asynchronous tasks using the `Callable` interface. Each task generates a data batch and requests access to the `SharedResource`. The main thread then collects the results synchronously using `Future` objects to ensure all tasks complete before shutting down the system.

## How to Run

To run the simulation, you must have the Java Development Kit (JDK) installed on your system.

1. Open your terminal or command prompt in the project directory.
2. Compile the Java source files:
   ```bash
   javac SharedResource.java lab4_ai.java

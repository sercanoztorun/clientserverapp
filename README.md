# Client Server Application
## Summary
This project consists of two parts: client and server. Client program that monitors a directory for new Java properties files, applies a configurable key filtering pattern, and sends filtered data to a server. The server should accept messages from multiple clients, reconstruct filtered properties files, and save them to a specified directory.
## Project Technical Details
### Client
The client application has been enhanced with multi-threading capabilities to improve its efficiency. 
It now validates the parameters in the specified config path more effectively. 
Additionally, it utilizes a WatchService to monitor for entry_create events.
When a new properties file is detected, a new thread is spawned to process it. 
The data from the file is selectively filtered and stored in a LinkedHashMap which is selected because of insertion order is important.
This LinkedHashMap is transmitted to the server via a socket connection for further processing. 
Once the server has successfully saved the file, receive "OK" message then it is deleted to maintain system
### Server
The server application has been enhanced with multi-threading capabilities to improve its efficiency.
It now validates the parameters in the specified config path more effectively.
It creates a ServerSocket and waits for clients to connect.
When a new client is connected, a new thread is spawned to process it.
It creates a new file with the same file name in %WRITE_DIRECTORY_PATH% and writes the incoming data. After successfully creating it, sends an 'OK' message to the client.
## Preparation
Crate a client config
```
MONITOR_DIRECTORY_PATH=/Users/sercanoztorun/Coding/ClientServerApp/clientDir/
KEY_FILTERING_PATTERN=^[A-Za-z0-9._]+$
SERVER_ADDRESS=127.0.0.1
SERVER_PORT=5001
```
Crate a server config
```
WRITE_DIRECTORY_PATH=/Users/sercanoztorun/Coding/ClientServerApp/serverDir/
SERVER_PORT=5001
```
## Build and Run
Clone this repository
### Client
1.Go to client/src directory for build

`javac *.java`

2.) Run the client application

`java Main %CLIENT_CONFIG_FILE_PATH%`


### Server
1.Go to server/src directory for build

`javac *.java`

2.) Run the client application

`java Main %SERVER_CONFIG_FILE_PATH%`






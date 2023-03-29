Documents and References

gRPC: https://grpc.io/

Protobuf 3 syntax: https://developers.google.com/protocol-buffers/docs/proto3



Tools

GUI Client for GRPC: https://github.com/uw-labs/bloomrpc



How to generate gRPC code

Java

mvn clean install

Go

Install protoc
Windows: choco install protoc
Linux: apt install -y protobuf-compiler
MacOS: brew install protobuf

$ protoc --version # Ensure compiler version is 3+
Set Global GOPATH
export $GOPATH=$HOME/go
export PATH=$PATH:$GOROOT/bin:$GOPATH/bin
Get library
go get -u google.golang.org/grpc
go get -u github.com/golang/protobuf/protoc-gen-go
Generate CMD
make gen-go

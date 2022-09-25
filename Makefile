gen-go:
	rm -rf go/
	rm -rf ../gateway-service/client/*/*
	mkdir go
	protoc --proto_path=src/main/proto src/main/proto/**/*.proto --go_out=go

	protoc --proto_path=src/main/proto src/main/proto/**/*.proto --go-grpc_out=./go

	ls go/*/*/*/*.pb.go | xargs -n1 -Ix bash -c 'sed s/,omitempty// x > x.tmp && mv x{.tmp,}'
	cp -rf go/gateway-service/client/* ../gateway-service/client

.PHONY: gen-go

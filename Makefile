gen-go:
	rm -rf go/
	mkdir go
	protoc --proto_path=src/main/proto src/main/proto/**/*.proto --go_out=go
	protoc --proto_path=src/main/proto src/main/proto/**/*.proto --go-grpc_out=./go
	rm -rf ../emarket-gateway-service/client/*/*

	ls go/*/*/*/*.pb.go | xargs -n1 -Ix bash -c 'sed s/,omitempty// x > x.tmp && mv x{.tmp,}'
	cp -rf go/emarket-gateway-service/client/* ../emarket-gateway-service/client
.PHONY: gen-go

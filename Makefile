local-env-create:
	docker-compose -f docker-compose.yml up -d
	sleep 3
	#docker cp database/ddl.sql postgres:/var/lib/postgresql/data
	docker exec postgres-carrier psql -h localhost -U admin -d postgres
#-a -f ./var/lib/postgresql/database/ddl.sql

local-env-destroy:
	docker-compose -f docker-compose.yaml down
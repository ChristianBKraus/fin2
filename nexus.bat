docker volume create --name nexus-data
docker run -d -p 8081:8090 --name nexus -v nexus-data:/nexus-data sonatype/nexus3

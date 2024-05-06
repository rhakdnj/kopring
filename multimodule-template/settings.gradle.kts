rootProject.name = "multimodule-template"

include(
  "applications:api",
  "storage:db-core",
  "clients:s3-client",
  "clients:redis-client",
  "shared:common",
  "shared:monitoring",
  "shared:api-docs",
)

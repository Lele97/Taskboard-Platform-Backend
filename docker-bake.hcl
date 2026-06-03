group "default" {
  targets = ["gateway", "auth", "project", "analytics"]
}

target "gateway" {
  context    = "."
  dockerfile = "Dockerfile"
  target     = "gateway-runtime"
  tags = ["taskboard-gateway:2.0"]
}

target "auth" {
  context    = "."
  dockerfile = "Dockerfile"
  target     = "auth-runtime"
  tags = ["taskboard-auth:2.0"]
}

target "project" {
  context    = "."
  dockerfile = "Dockerfile"
  target     = "project-runtime"
  tags = ["taskboard-project:2.0"]
}

target "analytics" {
  context    = "."
  dockerfile = "Dockerfile"
  target     = "analytics-runtime"
  tags = ["taskboard-analytics:2.0"]
}
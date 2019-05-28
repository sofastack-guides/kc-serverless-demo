# Book-info Demo

## Frontend 配置

- 监听端口：8080
- 入口方法：com.demo.alipay.Main::main
- 环境变量：
  - BACKEND_URL=bookinfo-backend.sas.alipay.com
  - LOG_ROOT_PATH=/home/admin/logs (选填)

## Backend 配置

- 监听端口：8080
- 入口方法：com.demo.alipay.Main::main
- 环境变量：
  - DATABASE_URL=your-db-url
  - DATABASE_USERNAME=your-db-username
  - DATABASE_PASSWORD=your-db-password
  - LOG_ROOT_PATH=/home/admin/logs (选填)

## Demo Roadmap

- 通过快速创建，创建后端应用
- 通过应用创建，创建前端应用 V1.0.0
- 查看应用 0-1 的响应时间
- 通过应用版本，创建前端应用 V2.0.0
- 配置路由，查看应用流量管控
- 通过 Log Shell，查看应用日志
- 其他功能介绍：触发器和监控

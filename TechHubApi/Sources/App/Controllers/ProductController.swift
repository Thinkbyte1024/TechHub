import Vapor
import Fluent

struct ProductController: RouteCollection {
  func boot(routes: RoutesBuilder) throws {
    routes.get("index", use: indexProduct)
    routes.get("delete", use: deleteProduct)
    routes.post("create", use: createProduct)
    routes.post("update", use: updateProduct)
  }

  // Buat data
  func createProduct(_ req: Request) async throws -> Response {
    let product = try req.content.decode(Product.self)

    try await product.save(on: req.db)
    return .init(status: .ok)
  }

  // Update data
  func updateProduct(_ req: Request) throws -> EventLoopFuture<Response> {
    guard let idParam: UUID? = req.query["id"] else {
      throw Abort(.badRequest)
    }

    let input = try req.content.decode(Product.self)

    return Product.find(idParam, on: req.db)
      .unwrap(or: Abort(.notFound)).flatMap { product in
        product.name = input.name
        product.price = input.price

        return product.save(on: req.db).map { product }.encodeResponse(status: .ok, for: req)
      }

  }

  // Index data berdasarkan ID atau keseluruhan
  func indexProduct(_ req: Request) async throws -> Response{
    guard let idParam: UUID? = req.query["id"] else {
      throw Abort(.badRequest)
    }

    if let id = idParam {
      return try await Product.query(on: req.db).filter(\.$id == id).all().encodeResponse(status: .ok, for: req)
    } else {
      return try await Product.query(on: req.db).all().encodeResponse(status: .ok, for: req)
    }
  }

  // Hapus data
  func deleteProduct(_ req: Request) throws -> EventLoopFuture<HTTPStatus> {
    guard let idParam: UUID? = req.query["id"] else {
      throw Abort(.badRequest)
    }

    return Product.find(idParam, on: req.db)
      .unwrap(or: Abort(.notFound)).flatMap { product in
        product.delete(on: req.db)
      }.transform(to: .ok)
  }
}

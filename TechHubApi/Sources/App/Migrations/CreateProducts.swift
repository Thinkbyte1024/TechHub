import Fluent

struct CreateProducts: Migration {
  func prepare(on database: Database) -> EventLoopFuture<Void> {
    database.schema(Product.schema)
      .id()
      .field("product_name", .string, .required)
      .field("product_price", .int, .required)
      .create()
  }

  func revert(on database: Database) -> EventLoopFuture<Void> {
    database.schema(Product.schema).delete()
  }
}

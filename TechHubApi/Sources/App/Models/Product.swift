import Fluent
import Vapor

final class Product: Model, Content {
  static var schema = "products"

  @ID(key: .id)
  var id: UUID?

  @Field(key: "product_name")
  var name: String

  @Field(key: "product_price")
  var price: Int

  init() {}

  init(_ id: UUID? = nil, name: String, price: Int) {
    self.id = id
    self.name = name
    self.price = price
  }
}

extension Product {
  struct Create: Migration {
    func prepare(on database: Database) -> EventLoopFuture<Void> {
      let products: [Product] = [
        .init(name: "Logitech H111 Headset", price: 87000),
        .init(name: "TCL 55A20 Smart TV", price: 6699000),
        .init(name: "Epson L120", price: 1989000),
        .init(name: "Epson Scanner Flatbed V39", price: 885000)
      ]

      return products.map { product in
        product.save(on: database)
      }
      .flatten(on: database.eventLoop)
    }

    func revert(on database: Database) -> EventLoopFuture<Void> {
      Product.query(on: database).delete()
    }
  }
}

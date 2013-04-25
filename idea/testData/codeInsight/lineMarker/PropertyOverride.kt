open class Base {
  open val readable: Int = 12
  open var writable: Int = 12
}

class SubBase: Base() {
  override val readable: Int = 42
  override var writable: Int = 42
}
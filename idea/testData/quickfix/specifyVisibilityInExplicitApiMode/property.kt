// "Make 'bar' public explicitly" "true"
// COMPILER_ARGUMENTS: -Xexplicit-api=strict

public class Foo(val <caret>bar: Int, private var bar2: String, internal var bar3: Long, public var bar4: Int)


package count

import generic.Versioned

case class Count(id: Int, name: String, n: Int)

case class CountViewObj(id: String, version: Long) extends Versioned

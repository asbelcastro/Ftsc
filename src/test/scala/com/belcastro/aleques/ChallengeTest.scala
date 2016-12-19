package com.belcastro.aleques

import org.junit.Assert
import org.junit.Test
import Challenge._

class ChallengeTest {

  val manyArgs: Array[String] = Array("/tmp/dummy.txt","AnotherArgument")
  val noArg: Array[String] = Array.empty
  val arg: Array[String] = Array("/tmp/dummy.txt")
  val testFile: String = "src/test/resources/data_scala.txt"

  @Test(expected = classOf[IllegalArgumentException])
  def noArgumentUsageTest: Unit = {
    usage(noArg)
  }

  @Test
  def usageTest: Unit = {
    usage(arg)
  }

  @Test(expected = classOf[IllegalArgumentException])
  def manyArgumentsUsageTest: Unit = {
    usage(manyArgs)
  }

  @Test(expected = classOf[IllegalArgumentException])
  def noFileGetFileContentParsedTest: Unit = {
    getFileContentParsed("dummy.txt")
  }

  @Test
  def getFileContentParsedTest: Unit = {
    val data = getFileContentParsed(testFile)
    Assert.assertTrue(data.size == 49)
  }

  @Test
  def timeWindowEndTest: Unit = {
    val tuple :(Long, Double) = (1355270609, 1.80215)
    val tuple1:(Long, Double) = (1355270621, 1.80185)
    val tuple2:(Long, Double) = (1355270646, 1.80195)
    Assert.assertEquals(1355270580, timeWindow(tuple))
    Assert.assertEquals(1355270580, timeWindow(tuple1))
    Assert.assertEquals(1355270640, timeWindow(tuple2))
  }

  @Test
  def toReportTest: Unit = {
    val tuple : (Long, List[(Long, Double)]) = (1355270640, List((1355270609L, 1.80215)))
    val tuple2: (Long, List[(Long, Double)]) = (1355270700, List((1355270609L, 1.80215), (1355270621, 1.80185)))
    val tuple3: (Long, List[(Long, Double)]) = (1355270580, List((1355270609L, 1.80215), (1355270621, 1.80185), (1355270646, 1.80195)))
    Assert.assertEquals((1355270640, 1.80215, 1, 1.80215, 1.80215, 1.80215), toReport(tuple))
    Assert.assertEquals((1355270700, 1.80215, 2, 3.604  , 1.80185, 1.80215), toReport(tuple2))
    Assert.assertEquals((1355270580, 1.80215, 3, 5.40595, 1.80185, 1.80215), toReport(tuple3))
  }

  @Test
  def processTest: Unit = {

    val data: List[(Long, Double)] =
      List((1355270609,1.80215),
        (1355270621,1.80185),
        (1355270646,1.80195),
        (1355270702,1.80225),
        (1355270702,1.80215),
        (1355270829,1.80235),
        (1355270854,1.80205),
        (1355270868,1.80225),
        (1355271000,1.80245),
        (1355271023,1.80285))

    val dataProcessed: List[(Long, Double, Int, Double, Double, Double)] =
      List((1355270820, 1.80235, 3, 5.40665, 1.80205, 1.80235),
        (1355271000, 1.80245, 2, 3.6053, 1.80245, 1.80285),
        (1355270580, 1.80215, 2, 3.604, 1.80185, 1.80215),
        (1355270640, 1.80195, 1, 1.80195, 1.80195, 1.80195),
        (1355270700, 1.80225, 2, 3.6044, 1.80215, 1.80225))

    Assert.assertEquals(dataProcessed, process(data))

  }

  @Test
  def toOutputTest: Unit = {
    val record :(Long, Double, Int, Double, Double, Double) = (1355270820,1.80235,3,5.40665,1.80205,1.80235)
    val record2:(Long, Double, Int, Double, Double, Double) = (1355271000,1.80245,2,3.6053,1.80245,1.80285)
    val record3:(Long, Double, Int, Double, Double, Double) = (1355270580,1.80215,2,3.604,1.80185,1.80215)

    val recordExpected  = "1355270820 1.80235 3   5.40665    1.80205 1.80235"
    val recordExpected2 = "1355271000 1.80245 2   3.60530    1.80245 1.80285"
    val recordExpected3 = "1355270580 1.80215 2   3.60400    1.80185 1.80215"

    Assert.assertEquals(recordExpected, toOutput(record))
    Assert.assertEquals(recordExpected2, toOutput(record2))
    Assert.assertEquals(recordExpected3, toOutput(record3))

  }

  @Test
  def getHeaderTest: Unit = {
    val headerExpected = "T          S       N   RS         MinV    MaxV   " +
      "\n--------------------------------------------------"

    Assert.assertEquals(headerExpected, getHeader)
  }

}

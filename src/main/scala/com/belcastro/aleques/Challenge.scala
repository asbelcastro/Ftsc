package com.belcastro.aleques

import java.io.File

import scala.annotation.tailrec
import scala.collection.mutable.ListBuffer
import scala.io.Source

object Challenge {

  val SPACE  = " "
  val TAB    = "\t"
  val HYPHEN = "-"

  val TIME_WINDOW_LENGTH: Int = 60

  def main(args: Array[String]): Unit = {
    usage(args)
    println(getHeader)
    processData(args(0))
  }

  def usage(args: Array[String]): Unit = {
    require(args.length == 1,
      """Please, provide the arguments properly.
        <TimeSerieFilePath> - Absolute path for time series file
        i.e. java -jar challenge-01.00.00.jar /tmp/data_scala.txt
      """)

    val timeSeriesFile = new File(args(0))
    require(timeSeriesFile.exists,
      s"Path: ${timeSeriesFile.getAbsolutePath} does not exist. Please provide a valid path for a time series file.")
  }

  def getHeader: String = {
    val T = "T"
    val V = "S"
    val N = "N"
    val RS = "RS"
    val MIN_V = "MinV"
    val MAX_V = "MaxV"
    f"$T%-11s$V%-8s$N%-4s$RS%-11s$MIN_V%-8s$MAX_V%-7s%n${HYPHEN * 50}"
  }

  def parse(line: String): (Long, Double) = {
    val columns = line.replace(TAB, SPACE).split(SPACE, -1)
    (columns(0).toLong, columns(1).toDouble)
  }

  def anotherTimeWindow(threshold: Long, measurement:(Long, Double)): Boolean = {
    measurement._1 - TIME_WINDOW_LENGTH >= threshold
  }

  def beginTimeWindow:((Long, Double)) => Long = {
    case (epoch, _) =>
      if (epoch % TIME_WINDOW_LENGTH == 0) epoch
      else epoch - (epoch % TIME_WINDOW_LENGTH)
  }

  def toOutput(record: (Long, Double, Int, Double, Double, Double)): String = {
      val sumRounded = BigDecimal(record._4).setScale(5, BigDecimal.RoundingMode.HALF_UP)
      f"${record._1}$SPACE${record._2}$SPACE${record._3}%-3s$SPACE$sumRounded%-10s$SPACE${record._5}$SPACE${record._6}"
  }

  def consolidate(measurements: ListBuffer[(Long, Double)]): (Long, Double, Int, Double, Double, Double) = {
    (beginTimeWindow(measurements.head),
      measurements.head._2,
      measurements.length,
      measurements.map(_._2).sum,
      measurements.map(_._2).min,
      measurements.map(_._2).max)
  }

  def processData(path: String): Unit = {
    val data: Iterator[String] = Source.fromFile(path).getLines
    if ( data.hasNext ) {

      val firstMeasurement = parse(data.next)
      var timeWindowThreshold = beginTimeWindow(firstMeasurement)
      var timeWindowData = ListBuffer.empty[(Long, Double)]

      @tailrec
      def processTimeWindow(measurement: (Long, Double)): Unit = {

        if (anotherTimeWindow(timeWindowThreshold, measurement) && timeWindowData.nonEmpty) {
          println(toOutput(consolidate(timeWindowData)))
          timeWindowThreshold = beginTimeWindow(measurement)
          timeWindowData.clear
        }
        timeWindowData += measurement

        if (data.hasNext) processTimeWindow(parse(data.next))
        else println(toOutput(consolidate(timeWindowData)))
      }

      processTimeWindow(firstMeasurement)
    }
  }

}

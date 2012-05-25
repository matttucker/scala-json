/*
 * Matt Tucker
 * Copyright (c) 2012. All rights reserved.
 */

package com.geozen

import org.junit.runner.RunWith
import org.specs2.mutable._
import org.specs2.mutable.Specification
import org.specs2.runner.JUnitRunner
import net.liftweb.json.JsonDSL._
import net.liftweb.json._
import net.liftweb.json.JsonParser
import net.liftweb.json.JsonParser.ParseException
import scala.collection.mutable.ListBuffer

@RunWith(classOf[JUnitRunner])
object JsonSpecs extends Specification {

  // for json extractions
  implicit val formats = DefaultFormats

  "json" should {

    //
    // Parsing 
    //
    "parse a json array of strings" in {
      //skipped("skipped")
      val json = parse(""" ["a", "b"] """);
      json.values must_== List("a", "b")
    }

    "parse a bad string and catch exception" in {
      val s = """["a", "b]"""

      try {
        JsonParser.parse(s)
        failure
      } catch {
        case e: ParseException => println(e); success
        case _ => failure
      }

    }

    "parse array object" in {
      val s = """{"array":["a", "b"]}"""

      val json = parse(s)
      println(json)
      success
    }

    "parse array object and render" in {
      val s = """{"array":["a", "b"]}"""

      val json = parse(s)
      println(compact(render(json)))
      println(pretty(render(json)))
      success
    }

    //
    // Extraction
    //
    "extract an integer from a field" in {
      val json = parse(""" { "a": 1, "b": 2 } """)
      val a = (json \ "a").extract[Int]
      a must_== 1
      val JInt(b) = (json \ "b")
      b must_== 2
    }

    "extract with match" in {
      val json = ("created_at" -> 33) ~ ("test" -> "a");
      println("json = " + json)

      (json \ "created_at") match {
        case JInt(timestamp) => println("timestamp: " + timestamp); timestamp must_== 33; success
        case JString(datestr) => println("datestr: " + datestr); failure
        case _ => failure
      }

    }

    "extract map" in {
      val json = parse(""" {  "a": 1, "b": 2 } """)
      val map = json.values.asInstanceOf[Map[String, Any]];
      map.foreach(x => println(x._1 + ":" + x._2))

      success
    }

    "list to jarray" in {
      val scopes = List("a", "b")
      println(compact(render(scopes)));
      success
    }

    "extract into a case class" in {
      case class Child(name: String, age: Int, birthdate: Option[java.util.Date])
      case class Address(street: String, city: String)
      case class Person(name: String, address: Address, children: List[Child])

      val json = parse("""
         { "name": "joe",
           "address": {
             "street": "Bulevard",
             "city": "Helsinki"
           },
           "children": [
             {
               "name": "Mary",
               "age": 5
               "birthdate": "2004-09-04T18:06:22Z"
             },
             {
               "name": "Mazy",
               "age": 3
             }
           ]
         }
       """)
      json.extract[Person]
      success
    }

    //
    // Merge
    //
    "merge two jsons" in {
      val json1 = JsonParser.parse(""" { "a": 1, "b": 2 } """)
      val json2 = JsonParser.parse(""" { "c": 3 } """)

      val result = json1 merge json2;

      println("merge=" + compact(render(result)));
      success
    }

    //
    // Misc
    //

    "empty jobject" in {
      val e = JObject(List());
      println("empty jobject = " + compact(render(e)))
      success
    }

    "json constructer with 1 or more fields" in {
      // 1 field
      val json1: JObject = ("created_at" -> 34)
      println(json1)

      // 1+ fields
      val json2 = ("created_at" -> 34) ~ ("b" -> "c")
      println(json2)

      success
    }

    "combine" in {

      val json1 = ("a" -> "1") ~
        ("b" -> 2);

      val json2 = ("a" -> "3") ~
        ("b" -> 4);

      var combine = ListBuffer[JValue]();

      combine += json1;
      combine += json2;

      val json: JValue = combine.toList

      println(pretty(render(json)));
      success
    }

  }
}






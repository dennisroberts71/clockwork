(use '[clojure.java.shell :only (sh)])
(require '[clojure.string :as string])

(defn git-ref
  []
  (or (System/getenv "GIT_COMMIT")
      (string/trim (:out (sh "git" "rev-parse" "HEAD")))
      ""))

(defproject org.iplantc/clockwork "4.1.9"
  :description "Scheduled jobs for the iPlant Discovery Environment"
  :url "http://www.iplantcollaborative.org"
  :license {:name "BSD"
            :url "http://iplantcollaborative.org/sites/default/files/iPLANT-LICENSE.txt"}
  :manifest {"Git-Ref" ~(git-ref)}
  :uberjar-name "clockwork-standalone.jar"
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [org.clojure/tools.logging "0.2.3"]
                 [cheshire "5.0.2"]
                 [clj-http "0.6.5"]
                 [clj-time "0.4.5"]
                 [clojurewerkz/quartzite "1.0.1"]
                 [com.cemerick/url "0.0.7"]
                 [korma "0.3.0-RC5"]
                 [log4j "1.2.17"]
                 [org.iplantc/clj-jargon "4.1.9"]
                 [org.iplantc/clojure-commons "4.1.9"]
                 [org.iplantc/common-cli "4.1.9"]
                 [org.iplantc/kameleon "4.1.9"]
                 [me.raynes/fs "1.4.4"]
                 [org.slf4j/slf4j-api "1.7.2"]
                 [org.slf4j/slf4j-log4j12 "1.6.6"]
                 [slingshot "0.10.3"]]
  :plugins [[org.iplantc/lein-iplant-rpm "4.1.9"]]
  :profiles {:dev     {:resource-paths ["resources/test"]}
             :uberjar {:aot :all}}
  :main ^:skip-aot clockwork.core
  :iplant-rpm {:summary "Scheduled jobs for the iPlant Discovery Environment"
               :provides "clockwork"
               :dependencies ["iplant-service-config >= 0.1.0-5"
                              "java-1.7.0-openjdk"]
               :config-files ["log4j.properties"]
               :config-path "resources/main"}
  :uberjar-exclusions [#"BCKEY.SF"]
  :repositories [["sonatype-nexus-snapshots"
                  {:url "https://oss.sonatype.org/content/repositories/snapshots"}]]
  :deploy-repositories [["sonatype-nexus-staging"
                         {:url "https://oss.sonatype.org/service/local/staging/deploy/maven2/"}]])

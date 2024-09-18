To run PMD static analyzer:
pmd check -d ./ -R rulesets/java/quickstart.xml -f text

To run the CheckStyle style checker:
mvn checkstyle:check

To run Jacoco code coverage report:
mvn jacoco:report

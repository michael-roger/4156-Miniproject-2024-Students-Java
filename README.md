# Welcome Students of 4156

Please follow the assignment specifications on Courseworks when completing this project.

To run PMD static analyzer:
pmd check -d ./ -R rulesets/java/quickstart.xml -f text

To run the CheckStyle style checker:
mvn checkstyle:check

To run Jacoco code coverage report:
mvn jacoco:report

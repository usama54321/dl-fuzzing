# dl-fuzzing
This repo contains basic tests written on top of [JQF](https://github.com/usama54321/jqf), using Java implementations of some DL applications from [https://github.com/usama54321/dl-java-apps](this) repo.

Requirements
1) Java >= 9. Tested with Java 11.
2) [mtcnn-java](https://github.com/usama54321/mtcnn-java). This is the tensorflow model.

Tests are defined under src/test. A basic test is present in the AppTest class with function name testProgramWithoutModel, which should be runnable by both:

```
mvn test #for testing with quickcheck
```
and
```
mvn jqf:fuzz -Djanala.verbose=true -Djqf.ei.MAX_INPUT_SIZE=99999999 -Dclass=edu.ucla.cs.AppTest -Dmethod=testProgramWithoutModel
```
## Setup Instructions:
```
git clone https://github.com/usama54321/mtcnn-java #mtcnn-java bugfixed dependency
cd mtcnn-java
git checkout dev
mvn package && mvn install

git clone https://github.com/usama54321/dl-fuzzing
cd dl-fuzzing
mvn package && mvn install
```

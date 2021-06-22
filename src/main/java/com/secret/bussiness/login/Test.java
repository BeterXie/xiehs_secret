package com.secret.bussiness.login;

import com.secret.bussiness.biz.entity.User;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author xiehs
 * @package com.secret.bussiness.login
 * @date 2019/12/10  20:31
 */
public class Test {

  static class Grade {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    static  class Teacher{

        private String name;
        private Integer age;
        private List<Grade> grades;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public List<Grade> getGrades() {
            return grades;
        }

        public void setGrades(List<Grade> grades) {
            this.grades = grades;
        }
    }

    static  class Student{
        private String name;
        private Integer age;
        private Grade grade;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Grade getGrade() {
            return grade;
        }

        public void setGrade(Grade grade) {
            this.grade = grade;
        }
    }


    public static void main(String[] args) {
        Grade grade1 = new Grade();
        grade1.setName("班级1");
        Grade grade2 = new Grade();
        grade2.setName("班级2");
        Grade grade3 = new Grade();
        grade3.setName("班级3");
        Grade grade4 = new Grade();
        grade4.setName("班级4");
        Grade grade5 = new Grade();
        grade5.setName("班级5");

        Teacher teacher1 = new Teacher();
        teacher1.setName("谢老师");
        teacher1.setAge(22);
        teacher1.setGrades(Arrays.asList(grade1,grade2));
        Teacher teacher2 = new Teacher();
        teacher2.setName("谢老师");
        teacher2.setAge(23);
        teacher2.setGrades(Arrays.asList(grade3,grade2));
        Teacher teacher3 = new Teacher();
        teacher3.setName("谢老师");
        teacher3.setAge(24);
        teacher3.setGrades(Arrays.asList(grade3,grade2));
        Teacher teacher4 = new Teacher();
        teacher4.setName("谢老师");
        teacher4.setAge(35);
        teacher4.setGrades(Arrays.asList(grade3,grade2));

        List<Teacher> teacherList = Arrays.asList(teacher1,teacher2,teacher3,teacher4);

        List<String> collect = teacherList.stream().map(Teacher::getGrades)
                .flatMap(Collection::stream)
                .filter(grade -> "班级3".equalsIgnoreCase(grade.getName())).map(Grade::getName).collect(Collectors.toList());

        System.out.println(collect.toString());


    }
}

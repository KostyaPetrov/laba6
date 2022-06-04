package collection;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Person implements Serializable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private LocalDateTime birthday; //Поле может быть null
    private Double weight; //Поле не может быть null, Значение поля должно быть больше 0

    public Person(String name, LocalDateTime birthday, Double weight) {
        this.name=name;
        this.birthday=birthday;
        this.weight=weight;
    }
    public String getNamePerson(){
        return name;
    }
    public LocalDateTime getBirthday(){
        return birthday;
    }
    public Double getWeight(){
        return weight;
    }
    @Override
    public String toString(){
//        if(name.equals(null) || birthday.equals(null) || weight.equals(null)){
//            return null;
//        }else if(!name.equals(null) || !birthday.equals(null) || weight.equals(null) ){
//            return String.format(" %s, %s", name, birthday);
//        }else if(name.equals(null) || birthday.equals(null) || weight.equals(null)){
//            return String.format(" %s, %s", name, weight);
//        }else if(birthday.equals(null) || weight.equals(null)) {
//            return String.format(" %s, %s", birthday, weight);
//        }else if()
//        }else{
//            return String.format(" %s, %s, %s", name, birthday, weight);
//        }
        return String.format(" %s, %s, %s", name, birthday, weight);
    }

}

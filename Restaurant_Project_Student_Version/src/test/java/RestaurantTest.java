//import com.sun.source.tree.ModuleTree;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    Restaurant restaurant;

    //>>>>>>>>>>>>>>>>>>>>>>>>>OPEN/CLOSED<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    //-------FOR THE 2 TESTS BELOW, YOU MAY USE THE CONCEPT OF MOCKING, IF YOU RUN INTO ANY TROUBLE
    @BeforeEach
    public void setup() {
        LocalTime openingTime = LocalTime.parse("11:00:00");
        LocalTime closingTime = LocalTime.parse("14:00:00");
        restaurant =new Restaurant("Amelie's cafe","Chennai",openingTime,closingTime);
        restaurant.addToMenu("Sweet corn soup",119);
        restaurant.addToMenu("Vegetable lasagne", 269);

    }

    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time(){
       // Act
        boolean is_restaurant_open= check_if_restaurant_open_is_true_or_false("11:00:01", "13:59:59");
        //Assert
        assertThat(true,equalTo(is_restaurant_open));
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time(){
        // Arrange & Act
        boolean is_restaurant_open= check_if_restaurant_open_is_true_or_false("11:00:00", "14:00:00");
        //Assert
        assertThat(false,equalTo(is_restaurant_open));
    }

    public boolean check_if_restaurant_open_is_true_or_false(String lowerThreshHoldTime, String upperThreshHoldTime){

        Restaurant spiedRestaurantcaseForLowerThreshHold = Mockito.spy(restaurant);
        Restaurant spiedRestaurantcaseForUpperThreshHold = Mockito.spy(restaurant);
        when(spiedRestaurantcaseForLowerThreshHold.getCurrentTime()).thenReturn(LocalTime.parse(lowerThreshHoldTime));
        when(spiedRestaurantcaseForUpperThreshHold.getCurrentTime()).thenReturn(LocalTime.parse(upperThreshHoldTime));
        if ( spiedRestaurantcaseForLowerThreshHold.isRestaurantOpen()== true && spiedRestaurantcaseForUpperThreshHold.isRestaurantOpen() == true )
            return true;
        else if (spiedRestaurantcaseForLowerThreshHold.isRestaurantOpen()== false && spiedRestaurantcaseForUpperThreshHold.isRestaurantOpen() == false )
            return false;

        throw new RuntimeException();
    }


    //<<<<<<<<<<<<<<<<<<<<<<<<<OPEN/CLOSED>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //>>>>>>>>>>>>>>>>>>>>>>>>>>>MENU<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1(){

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie",319);
        assertEquals(initialMenuSize+1,restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {

        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize-1,restaurant.getMenu().size());
    }
    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {

        assertThrows(itemNotFoundException.class,
                ()->restaurant.removeFromMenu("French fries"));
    }
    //<<<<<<<<<<<<<<<<<<<<<<<MENU>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
}
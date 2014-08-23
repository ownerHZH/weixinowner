package com.owner.onsants;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.owner.entity.App;
import com.owner.entity.Flight;
import com.owner.entity.Groupon;
import com.owner.entity.Hotel;
import com.owner.entity.Lottery;
import com.owner.entity.News;
import com.owner.entity.Novel;
import com.owner.entity.Price;
import com.owner.entity.Privilege;
import com.owner.entity.Restaurant;
import com.owner.entity.Train;
import com.owner.utils.GsonUtil;

public class Consant {

   public static final String API_KEY="1cd20475b702bc4819831e88e2292d97";
   public static final String SERVER_URL="http://www.tuling123.com/openapi/api";
   
   public static final Gson gson = GsonUtil.getGson();
   
   //public static final Type data_type=(Type) new TypeToken<List<Train>>(){}.getType();
   
   public static final Type app_list_type=(Type) new TypeToken<List<App>>(){}.getType();
   public static final Type flight_list_type=(Type) new TypeToken<List<Flight>>(){}.getType();
   public static final Type groupon_list_type=(Type) new TypeToken<List<Groupon>>(){}.getType();
   public static final Type hotel_list_type=(Type) new TypeToken<List<Hotel>>(){}.getType();
   public static final Type lottery_list_type=(Type) new TypeToken<List<Lottery>>(){}.getType();
   public static final Type news_list_type=(Type) new TypeToken<List<News>>(){}.getType();
   public static final Type novel_list_type=(Type) new TypeToken<List<Novel>>(){}.getType();
   public static final Type price_list_type=(Type) new TypeToken<List<Price>>(){}.getType();
   public static final Type privilege_list_type=(Type) new TypeToken<List<Privilege>>(){}.getType();
   public static final Type restaurant_list_type=(Type) new TypeToken<List<Restaurant>>(){}.getType();
   public static final Type train_list_type=(Type) new TypeToken<List<Train>>(){}.getType();
}

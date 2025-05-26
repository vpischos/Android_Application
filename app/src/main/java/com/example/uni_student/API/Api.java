package com.example.uni_student.API;

import com.example.uni_student.models.CoursesResponse;
import com.example.uni_student.models.DefaultResponse;
import com.example.uni_student.models.GradeResponse;
import com.example.uni_student.models.StudentResponse;
import com.example.uni_student.models.UsersResponse;
import com.example.uni_student.models.loginResponse;

import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Call;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface Api {

    @FormUrlEncoded
    @POST("createUser")
    Call<DefaultResponse> createUser(
        @Field("email") String email,
        @Field("password") String password,
        @Field("name") String name,
        @Field("AEM") String AEM,
        @Field("university") String university
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<loginResponse> userlogin(
            @Field("email") String email,
            @Field("password") String password,
            @Field("AEM") String AEM
    );


    @GET("allusers")
    Call<UsersResponse> getUsers();

    @GET("allprofs")
    Call<UsersResponse> getProfs();

    @GET("studentsCourses/{AEM}")
    Call<CoursesResponse> studentCourses(@Path("AEM") String AEM);

    @GET("getEnrolledStudents/{AEM}")
    Call<StudentResponse> getEnrolledStudents(@Path("AEM") String AEM);

    @GET("getGrades/{AEM}")
    Call<GradeResponse> getGrades(@Path("AEM") String AEM);

    @FormUrlEncoded
    @PUT("updateuser/{AEM}")
    Call<loginResponse> updateUser(
            @Path("AEM") String AEM,
            @Field("email") String email,
            @Field("name") String name
    );

    @FormUrlEncoded
    @POST("checkEmail")
    Call<DefaultResponse> checkEmail(
            @Field("email") String email,
            @Field("AEM") String AEM
    );

    @FormUrlEncoded
    @POST("checkStudent")
    Call<DefaultResponse> checkStudent(
            @Field("AEM") String AEM
    );

    @FormUrlEncoded
    @POST("insertOfferTo")
    Call<DefaultResponse> insertOfferTo(
            @Field("FK1_University_code") int FK1_University_code,
            @Field("AEM") String AEM,
            @Field("Subject_name") String Subject_name
    );

    @FormUrlEncoded
    @POST("insertGradeOn")
    Call<DefaultResponse> insertGradeOn(
            @Field("AEM") String AEM,
            @Field("S_AEM") String S_AEM,
            @Field("Subject_name") String Subject_name,
            @Field("Grade") int Grade
    );

    @FormUrlEncoded
    @PUT("updatepassword")
    Call<DefaultResponse> updatePassword(
            @Field("currentpassword") String currentpassword,
            @Field("newpassword") String newpassword,
            @Field("email") String email
    );

    @DELETE("deleteuser/{AEM}")
    Call<DefaultResponse> deleteUser(@Path("AEM") String AEM);


}

package com.msm.floodoor;

import android.content.Context;
import android.util.Log;

import com.amazonaws.auth.CognitoCachingCredentialsProvider;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Expression;
import com.amazonaws.mobileconnectors.dynamodbv2.document.ScanFilter;
import com.amazonaws.mobileconnectors.dynamodbv2.document.Table;
import com.amazonaws.mobileconnectors.dynamodbv2.document.datatype.Document;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.cognitoidentity.AmazonCognitoIdentity;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.lambda.AWSLambdaClient;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;

import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

public class AWSHelper {

    private static AWSHelper awsHelper;

    private CognitoCachingCredentialsProvider credentialsProvider;

    private AmazonDynamoDBClient dbClient;
    private AWSLambdaClient lambdaClient;

    private Table noticesTable;

    AWSHelper(Context context){
        credentialsProvider = new CognitoCachingCredentialsProvider(
                context,
                "ap-south-1:15ee3b07-8358-4a74-a9d8-ea6348de84bf", // Identity pool ID
                Regions.AP_SOUTH_1 // Region
        );
        dbClient=new AmazonDynamoDBClient(credentialsProvider);
        dbClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
        lambdaClient=new AWSLambdaClient(credentialsProvider);
        lambdaClient.setRegion(Region.getRegion(Regions.AP_SOUTH_1));
    }

    public static AWSHelper createInstance(Context context){
        if(awsHelper==null){
            awsHelper=new AWSHelper(context);
        }
        return awsHelper;
    }

    public List<NoticeItem> getAllNotices(){
        List<NoticeItem> noticeItemList=new ArrayList<>();
        try {
            noticesTable = Table.loadTable(dbClient, "FloodoorNotices");
            Expression expression = new Expression();
            List<Document> noticeDocs = noticesTable.scan(expression).getAllResults();

            Log.i("abcdef", String.valueOf(noticeDocs.size()));
            for (Document doc : noticeDocs) {
                NoticeItem item = new NoticeItem();
                item.setNid(doc.get("nid").asString());
                item.setDesc(doc.get("desc").asString());
                if(doc.containsKey("uri")){
                    item.setUri(doc.get("uri").asString());
                }
                noticeItemList.add(item);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return noticeItemList;
    }

    public JSONObject InvokeRainfallPred(String state,String month,String year){
        JSONObject resultObject=new JSONObject();
        try {
            InvokeRequest invokeRequest=new InvokeRequest();
            invokeRequest.setFunctionName("RainfallPred");
            JSONObject payloadJSON=new JSONObject();
            payloadJSON.put("state",state);
            payloadJSON.put("month",month);
            payloadJSON.put("year",year);
            byte[] payloadBytes=payloadJSON.toString().getBytes("utf-8");
            ByteBuffer paloadBuffer=ByteBuffer.wrap(payloadBytes);
            invokeRequest.setPayload(paloadBuffer);
            InvokeResult result=lambdaClient.invoke(invokeRequest);
            String resultString=new String(result.getPayload().array());
            resultObject=new JSONObject(resultString);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }

    public JSONObject InvokeFloodPred(String river,String month,String year){
        JSONObject resultObject=new JSONObject();
        try {
            InvokeRequest invokeRequest=new InvokeRequest();
            invokeRequest.setFunctionName("FloodPred");
            JSONObject payloadJSON=new JSONObject();
            payloadJSON.put("river",river);
            payloadJSON.put("month",month);
            payloadJSON.put("year",year);
            byte[] payloadBytes=payloadJSON.toString().getBytes("utf-8");
            ByteBuffer paloadBuffer=ByteBuffer.wrap(payloadBytes);
            invokeRequest.setPayload(paloadBuffer);
            InvokeResult result=lambdaClient.invoke(invokeRequest);
            String resultString=new String(result.getPayload().array());
            resultObject=new JSONObject(resultString);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }

    public JSONObject InvokeAddToken(String river,String token){
        JSONObject resultObject=new JSONObject();
        try {
            InvokeRequest invokeRequest=new InvokeRequest();
            invokeRequest.setFunctionName("FloodoorAddToken");
            JSONObject payloadJSON=new JSONObject();
            payloadJSON.put("river",river);
            payloadJSON.put("token",token);
            byte[] payloadBytes=payloadJSON.toString().getBytes("utf-8");
            ByteBuffer paloadBuffer=ByteBuffer.wrap(payloadBytes);
            invokeRequest.setPayload(paloadBuffer);
            InvokeResult result=lambdaClient.invoke(invokeRequest);
            String resultString=new String(result.getPayload().array());
            resultObject=new JSONObject(resultString);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return resultObject;
    }
}

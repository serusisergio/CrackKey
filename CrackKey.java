/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crackkey;

/**
 *
 * @author serus
 */
public class CrackKey {
    static int language = 0;//0 se italiano, 1 se inglese
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String plainText;
        if(language==0){
            plainText = "Nel mezzodelcammindinostravitamiritrovaiperunaselvaoscuracheladirittaviaerasmarritaquantoadirqualeraecosaduraestaselvaselvaggiaeaspraefortechenelpensierrinovalapauratantoeamarachepocoepiumortemapertrattardelbenchivitrovaidirodelaltrecosechevihoscorteiononsobenridircomevientraitantoerapiendisonnoaquelpuntochelaveraceviaabbandonaimapoichifuialpieduncollegiuntoladoveterminavaquellavallechemaveadipaurailcorcompuntoguardaiinaltoevidilesuespallevestitegiaderaggidelpianetachemenadrittoaltruiperognecalleallorfulapauraunpocoquetachenellagodelcormeraduratalanottechipassaicontantapietaecomequeicheconlenaaffannatauscitofuordelpelagoalarivasivolgealacquaperigliosaeguata";
        }else{
            plainText = "I stand here today humbled by the task before us, grateful for the trust you have bestowed, mindful of the sacrifices borne by our ancestors. I thank President Bush for his service to our nation, as well as the generosity and cooperation he has shown throughout this transition.Forty-four Americans have now taken the presidential oath. The words have been spoken during rising tides of prosperity and the still waters of peace. Yet, every so often the oath is taken amidst gathering clouds and raging storms. At these moments, America has carried on not simply because of the skill or vision of those in high office, but because We the People have remained faithful to the ideals of our forbearers, and true to our founding documents.So it has been. So it must be with this generation of Americans.That we are in the midst of crisis is now well understood. Our nation is at war, against a far-reaching network of violence and hatred. Our economy is badly weakened, a consequence of greed and irresponsibility on the part of some, but also our collective failure to make hard choices and prepare the nation for a new age. Homes have been lost; jobs shed; businesses shuttered. Our health care is too costly; our schools fail too many; and each day brings further evidence that the ways we use energy strengthen our adversaries and threaten our planet. ";
        }
        String cipherText="";
        int dimText;
        int key          = 3;
        
        plainText = plainText.toLowerCase();
        plainText = plainText.replace(" ", "");
        plainText = plainText.replace(".", "");
        plainText = plainText.replace(",", "");
        plainText = plainText.replace("-", "");
        plainText = plainText.replace(";", "");
        
        
        
        
        //Crittazione testo
        for(int i=0; i<plainText.length();i++){
            char lettera = plainText.charAt(i);
            int let = (((lettera-97)+key)%26)+97; 
            cipherText = (String) (cipherText + (char)(let)); 
        }
        dimText = plainText.length();
        System.out.println(cipherText);
        
        
        for(int testKey=0;testKey<26;testKey++){
            plainText="";
            int[] freq= new int[26];
            double sum=0.0;
            int max=0;//cerco la lettera più frequente
            for(int i=0;i<cipherText.length();i++){
                char lettera = cipherText.charAt(i);
                int let;
                if(((lettera-97)-testKey)<0){
                    let = (((lettera-97)-testKey)+26)+97;
                }else{
                    let = (((lettera-97)-testKey)%26)+97;
                }
                plainText = plainText + (char)let;
                freq[((int)let)-97]++;
            }
            System.out.println("Per K ="+testKey);
            System.out.println(plainText);  
            for(int i=0;i<26;i++){
                if(freq[i]>1){
                    sum = sum + (freq[i]*(freq[i]-1));
                }
                //System.out.println(freq[i]);
            }
            System.out.println("L'indice I vale:"+(double)(sum/(dimText*(dimText-1))));
            System.out.println("  ");  
        }
        
        
              

    }
    
}

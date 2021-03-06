package fyp.plantsapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import fyp.plantsapp.Adapters.disease_list_adapter;
import fyp.plantsapp.Model.Diseases;

public class diseases_list extends AppCompatActivity {
RecyclerView diseases_list;
ArrayList<Diseases> diseases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases_list);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        diseases=new ArrayList<>();
        diseases_list=findViewById(R.id.diseases_list);
        diseases_list.setLayoutManager(new LinearLayoutManager(this));
        populate_diseases_list();
        diseases_list.setAdapter(new disease_list_adapter(this,diseases));
    }

    private void populate_diseases_list(){
        List<String> preventive_measures1=new ArrayList<>();
        preventive_measures1.add("اگر دستیاب ہو تو بیماری کی مزاحمتی قسموں کو فروغ دیں.");
        preventive_measures1.add("مناسب نائٹروجن کھاد کو یقینی بنائیں اضافی نائٹروجن کے استعمال سے بچیں.");
        preventive_measures1.add("فصلوں کے رضاکارانہ پودوں کو باقاعدگی سے دیکھیں اور انہیں ہٹا دیں.");
        preventive_measures1.add("فصل کی کاشت کے بعد مٹی میں گہرائی سے ہل چلائیں اور فصلوں کے فضلے کو مٹی میں دبا دیں.");
        List<String> symptoms1=new ArrayList<>();
        symptoms1.add("دھاتوں کی شکل پر چھوٹے، خشک دھبے بنتے ہیں.");
        symptoms1.add("شاخیں اور سر بھی متاثر ہو سکتے ہیں.");
        List<String> sprays1=new ArrayList<>();
        sprays1.add("بازار میں بہت سے بائیوفنگیسائیڈ دستیاب ہیں۔ 7 سے 14 دنوں کے وقفے پر لاگو بیسیلس پیومیلس پر مبنی مصنوعات پھپھوندی کے خلاف مؤثر ثابت ہوتی ہیں اور صنعت کے بڑے اداکاروں کی طرف سے مارکیٹنگ کی جاتی ہے۔");
        sprays1.add("ہمیشہ  حیاتیاتی علاج کے ساتھ  حفاظتی اقدامات پر ایک مربوط نقطہ نظر پر غور کریں، اگر دستیاب ہوتو۔سٹروبورین کلاس سے تعلق رکھنے والے فطر کش ادویات کے برگی اسپرے بیماری کے خلاف مؤثر تحفظ فراہم کرتے ہیں جب درخواست کو مکمل کر دیا جاتا ہے۔ پہلے سے ہی متاثرہ فصلوں میں، ٹرازازول خاندان یا دونوں کی مصنوعات کے مرکب سے متعلق مصنوعات کا استعمال کرنا چاہیئے۔");
        diseases.add(new Diseases(" پیلی زنگ کی دھاری ","فطر",sprays1,symptoms1,preventive_measures1,R.drawable.yellow_rust,"پکسینیا سٹریفارمس پھپھوندی کی وجہ سے علامات ہوتی ہیں جو ایک ذمہ دار جراثیم ہے۔ تخمک ہوا کی وجہ سے سینکڑوں کلومیٹر تک پھیلے جاتے ہیں اور اس بیماری کے موسمی وبا شروع کر دیتے ہیں۔ پھپھوندی سٹوماٹا کے ذریعہ پلانٹ میں داخل ہوتا ہے اور آہستہ آہستہ پتے کے ٹشو کو کالونائیز کرتا ہے۔ یہ بیماری بنیادی طور پر بڑھتی ہوئی موسم میں ابتدائی ہوتی ہے۔ فنگس اور انفیکشن کی ترقی کے لئے: زیادہ اونچائی، زیادہ نمی، بارش،اور 7 سے 15 کے درمیان ٹھنڈا درجہ حرارت، قابل اطمینان حالات ہیں۔ انفیکشن کو ختم کرنا ہوتا ہے جب درجہ حرارت مسلسل 21-23 سے زائد سینٹی میٹر تک پہنچ جاتا ہے، کیونکہ ان درجہ حرارت پر فنگس کی زندگی کا سلسلہ بقایا رہتا ہے۔ متبادل میزبان گندم، جڑی اور رائی ہیں۔."));
        List<String> preventive_measures2=new ArrayList<>();
        preventive_measures2.add("اگر دستیاب ہوں تو مزاحم انواع کا استعمال کریں.");
        preventive_measures2.add("موسم کے بالکل آغاز میں مت بوئیں.");
        preventive_measures2.add("بونے کی کثافت میں اچھی ذراعت کی اچھی ہواداری اور نمی کم کرنے کیلئے مناسب ترمیمات کریں.");
        preventive_measures2.add("مرض کی پہلی علامات کیلئے کھیت کو باقاعدگی سے مانیٹر کریں.");
        preventive_measures2.add("نائٹروجن کے استعمال کا احتیاط سے نظم کریں کیونکہ مٹی میں اس کی زیادہ مقدار مرض زا کی نشوونما کی حوصلہ افزائی کرتی ہے.");
        preventive_measures2.add("غیر میزبان پودوں کے ساتھ فصل بدل کر لگائیں.");
        List<String> symptoms2=new ArrayList<>();
        symptoms2.add("سفید، ملائم نشانات پتوں، تنوں اور خوشوں پر نمودار ہوتے ہیں.");
        symptoms2.add("کچھ فصلوں میں یہ نشانات بڑے، ابھرے ہوئے آبلوں کے بطور بھی نمودار ہو سکتے ہیں.");
        symptoms2.add("موسم کے آخر میں، نمایاں سیاہ دھبے سفید نشانات کے درمیان نمودار ہو سکتے ہیں.");
        symptoms2.add("قریب قریب بوئے گئے پودے، نائٹروجن کا ضرورت سے زیادہ استعمال اور ایک فصل کی کاشت اس مرض کیلئے موزوں حالات فراہم کرتے ہیں.");
        symptoms2.add("یہ سفوفی علاقے مرض کے بڑھنے کے ساتھ ساتھ خاکستری مائل سانولے رنگ کے ہو جاتے ہیں.");
        List<String> sprays2=new ArrayList<>();
        sprays2.add(" دودھ کے محلول کو بھی چھوٹے نامیاتی کاشتکاروں اور باغبانوں نے سفوفی پھپھوندی کے خلاف علاج کے طور پر کامیابی سے استعمال کیا ہے۔ دودھ کو پانی ملا کر پتلا کر دیا جاتا ہے (عموماً 1:10) اور انفیکشن کی پہلی علامات پر حساس پودوں پر اسپرے کیا جاتا ہے یا پھر بچاؤ کی تدبیر کے طور اسپرے کیا جاتا ہے۔ دہرائے جانے والے اطلاقات مرض کو قابو کرنے یا ختم کرنے کیلئے درکار ہیں۔");
        sprays2.add(" اگر دستیاب ہو تو ہمیشہ حیوی معالجات کے ساتھ بچاؤ کی تدابیر والی ایک مکمل حکمت عملی اختیار کریں۔ ڈائی فینوکونازول کے ساتھ بیج کا علاج جس کے بعد فلوٹریافول، ٹری ٹیکونازول اور ٹرائی ایڈی مینول سے ٹریٹ کرنا گندم کو اس کے اور دیگر فطر کے امراض کے خلاف بچانے کیلئے استعمال ہوتا تھا۔ فطر کش ادویات جیسے کہ بینومائل، فین پروپیڈین، فیرانیمول، ٹرائی ایڈی میفون، ٹیبیوکونازول، سائپروکونازول اور پروپیکونازول کے ساتھ بھی شافی کیمیائی کنٹرول ممکن ہے۔ پودوں کی حفاظت کرنے کا ایک اور طریقہ یہ بھی ہے کہ انہیں سیلیکون یا کیلشیئم سیلیکیٹ پر مبنی محلول سے ٹریٹ کیا جائے جو اس مرض زا کے خلاف پودے میں مزاحمت کی قوت کو بڑھاتا ہے۔");
        diseases.add(new Diseases(" غلہ پر سفوفی پھپھوندی ","طر",sprays2,symptoms2,preventive_measures2,R.drawable.powdery_mildrew,"علامات فطر بلومیریا گرامینس کی وجہ سے ہوتی ہیں جو ایک لمبوترا پودے کا مرض ہے جو صرف زندہ میزبان پر ہی نشوونما پا سکتا ہے اور باز تخلیقی عمل سے گزر سکتا ہے۔ اگر کوئی میزبان دستیاب نہ ہوں تو یہ موسموں کے درمیان سردیاں معطل اجسام کے بطور کھیت میں پودے کے فضلے پر گزارتا ہے۔ اناج کے علاوہ، یہ درجنوں دیگر پودوں پر کالونی بنا سکتا ہے جن کو یہ دو موسموں کے درمیان پل کے بطور استعمال کرتا ہے۔ جب حالات سازگار ہوں تو یہ نشوونما دوبارہ شروع کر دیتا ہے اور ایسے تخمک پیدا کرتا ہے جو بعد میں ہوا کے ذریعے صحت مند پودوں میں پھیل جاتے ہیں۔ ایک بار جب یہ پتے پر گرتا ہے تو تخمک نمود پاتے ہیں اور غذائیت حاصل کرنے والے ایسے اجسام پیدا کرتے ہیں جو میزبان خلیات سے غذائیت لے کر فطر کو نشوونما کو سپورٹ کرتے ہیں۔ نسبتاً ٹھنڈے اور پُر نم حالات (95٪ نمی) اور ابر آلود موسم اس کی نشوونما کی حوصلہ افزائی کرتے ہیں۔ البتہ، پتے کی نمی ان تخمکوں کی نمو کیلئے درکار نہیں ہے اور الٹا اسے روک سکتی ہے۔ مثالی درجہ حرارت 16 اور 21 ڈگری سینٹی گریڈ کے درمیان ہو سکتے ہیں اور 25 ڈگری سینٹی گریڈ سے اوپر کے درجہ حرارت ضرر رساں ہوتے ہیں۔ اس مرض زا کیلئے کوئی معلوم قرنطینہ کے ضوابط موجود نہیں ہیں بوجہ اس کی وسیع تقسیم اور ہوا کے ذریعے پھیلنا۔"));
        List<String> preventive_measures3=new ArrayList<>();
        preventive_measures3.add(" کھیتوں کے ارد گرد مختلف اقسام کے پودوں کی بلند تعداد برقرار رکھیں. ");
        preventive_measures3.add(" ایفیڈز کی حملہ آور آبادیوں کو بھگانے کیلئے منعکس ملچز استعمال کریں. ");
        preventive_measures3.add(" مرض یا کیڑے کے وقوع کی جانچ کرنے اور ان کی سنگینی کا تعین کرنے کیلئے کھیتوں کو باقاعدگی سے مانیٹر کریں. ");
        preventive_measures3.add(" ہاتھوں کی مدد سے ایفیڈ کو پودے پر سے اٹھایئں یا متاثرہ پودے کے حصوں کو ہٹا دیں. ");
        preventive_measures3.add(" کھیت کے اندر اور ارد گرد فالتو جھاڑیوں کو چیک کریں. ");
        preventive_measures3.add(" زیادہ پانی یا زیادہ کھاد نہ دیں. ");
        preventive_measures3.add(" حشرات کش ادویات کے استعمال کو کنٹرول کریں تاکہ دیگر مفید کیڑے متاثر نہ ہوں. ");
        List<String> symptoms3=new ArrayList<>();
        symptoms3.add(" پتے اور شاخیں مڑ جاتی ہیں.  ");
        symptoms3.add(" پتے اور شاکیں مرجھا اور پیلی ہو جاتی ہیں. ");
        symptoms3.add(" پودے کی نشوونما رک جاتی ہے. ");
        List<String> sprays3=new ArrayList<>();
        sprays3.add(" فائدہ مند کیڑے جیسا کہ شکار خور لیڈی یگز۔ لیس ونگ، سولجر بیٹلس اور طفیلی بھڑیں ایفیڈ کی آبادی کو قابو کرنے کے لیے اہم ایجنٹ ہیں۔ قدرتی دشمن کھیت کے حالات میں چوسنے والے کیڑوں کا خیال رکھ لیں گے۔ متوسط ابتلاء کی صورت میں، ایک سادہ نرم حشرات کش دوائی پر مبنی صابن کا محلول یا پودوں کے تیل پر مبنی محلول استعمال کریں مثال کے طور پر نیم کا تیل ( 3ایم ایل/ایل)۔ ایفیڈز نمی میں فطر کے امراض کیلئے بھی بے حد حساس ہوتے ہیں۔ متاثرہ پودوں پر ایک پانی کا ایک سادہ اسپرے انہیں بھی ہٹا سکتا ہے۔ ");
        sprays3.add(" ہمیشہ حیاتیاتی علاج (اگر دستیاب ہو) کے ساتھ  مل کر حفاظتی اقدامات کے ساتھ ساتھ ایک مربوط نقطہ نظر پر غور کریں۔ آگاہ رہیں کہ کیمیائی حشرات کش ادویات کا استعمال ایفیڈز میں ان کیلئے مزاحمت پیدا کر سکتا ہے۔ فلونیکامیڈ اور پانی 1:20 کے تناسب سے بونے (DAS) کے 30، 45، 60 دن بعد تنے پر اطلاق کرنے کا منصوبہ بنایا جا سکتا ہے۔ فپرونل 2 ملی لیٹر یا تھیامیتھوگزام 0.2 گرام یا فلونیکامیڈ 0.3 گرام یا ایسیٹامیپریڈ 0.2 گرام (فی لیٹر پانی) کا بھی استعمال ہو سکتا ہے۔ تاہم، ان کیمیکلز کے شکار خوروں، طفیلی کیڑوں اور زیرگی کنندگان پر منفی اثرات پڑ سکتے ہیں۔ ");
        diseases.add(new Diseases(" ایفیڈز (سبز مکھی یا تیلی) ","کیڑا",sprays3,symptoms3,preventive_measures3,R.drawable.aphid,"ایفیڈ چھوٹے، طویل ٹانگوں اور اینٹینا کے ساتھ نرم جسم والے کیڑے ہوتے ہیں۔  انکا سائر 0.5 سے 2 ملی میٹر تک ہوتا ہے اور انکے جسم کا رنگ انکی نسل کے لحاظ سے پیلا، بھورا، سرخ یا کالا ہوسکتا ہے۔ یہ بغیر والی اقسام کے بھی ہوتے ہیں جو کہ زیادہ عام ہیں اور پروں والے، مومی یا اون کی قسم کے بھی۔ یہ عام طور پر جوان پتوں اور شاخوں کے کناروں کے باہری طرف گروہ کی شکل میں رہتے اور کھاتے ہیں۔ یہ اپنے منہ کے لمبے حصوں کو پودے کے نرم ٹشوز کے آر پار کرنے اور اس میں سے مائع کو چوسنے کے لیے استعمال کرتے ہیں۔ کم سے متوازن تعداد میں فصلوں کو نقصان نہیں پہنچا سکتے۔  بہار کے آخر اور گرمیوں کی ابتدا میں ایک حملے کے بعد ان کی تعداد میں قدرتی دشمن کی وجہ سے کمی ہوتی ہے۔ کئی نسلیں پودے کا وائرس اپنے ساتھ لیے ہوتی ہیں جو دیگر بیماریوں کا سبب بن سکتا ہے۔"));
    }
}
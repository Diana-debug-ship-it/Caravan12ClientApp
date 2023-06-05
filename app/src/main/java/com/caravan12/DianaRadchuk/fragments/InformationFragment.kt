package com.caravan12.DianaRadchuk.fragments

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.caravan12.DianaRadchuk.R
import com.caravan12.DianaRadchuk.adapters.ArticlesRVAdapter
import com.caravan12.DianaRadchuk.databinding.DialogArticleBinding
import com.caravan12.DianaRadchuk.databinding.DialogContactsBinding
import com.caravan12.DianaRadchuk.databinding.FragmentInformationBinding

class InformationFragment : Fragment() {

    private lateinit var binding:FragmentInformationBinding
    private lateinit var dialogBinding:DialogContactsBinding
    private lateinit var dialogArticleBinding: DialogArticleBinding

    private lateinit var adapter: ArticlesRVAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var questionsArray: Array<String>
    private lateinit var answersArray: Array<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentInformationBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        openContacts()
        setData()
        setAdapter()

        binding.imageViewWhatsApp.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://api.whatsapp.com/send?phone=+79172688408&text=Пишу из приложения Caravan-12"))
            startActivity(intent)
        }
        binding.buttonMakeCall.setOnClickListener{
            val phone_intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:+78552380012"))
            startActivity(phone_intent)
        }

        binding.imageViewVk.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://vk.com/karavan12"))
            startActivity(intent);
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = InformationFragment
    }

    private fun openContacts() {
        binding.textViewContacts.setOnClickListener{
            dialogBinding = DialogContactsBinding.inflate(layoutInflater)
            val mBuilder = AlertDialog.Builder(activity).setView(dialogBinding.root)
            val mAlertDialog = mBuilder.show()

            dialogBinding.dismissDialogButton.setOnClickListener{
                mAlertDialog.dismiss()
            }
        }
    }

    private fun openArticle(title:String, textArticle:String){
        dialogArticleBinding = DialogArticleBinding.inflate(layoutInflater)
        val mBuilder = AlertDialog.Builder(activity).setView(dialogArticleBinding.root)
        dialogArticleBinding.apply {
            textViewTitle.text = title
            textViewArticle.text = textArticle
        }
        val mAlertDialog = mBuilder.show()

        dialogArticleBinding.floatingActionButtonBack.setOnClickListener{
            mAlertDialog.dismiss()
        }
    }

    private fun setAdapter() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView = binding.recyclerViewArticles
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        adapter = ArticlesRVAdapter(questionsArray)
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener(object : ArticlesRVAdapter.OnItemClickListener{
            override fun onItemClick(position: Int) {
                openArticle(questionsArray[position],answersArray[position])
            }
        })
    }

    private fun setData() {
        questionsArray = arrayOf(
            getString(R.string.questionAboutUs),
            getString(R.string.questionHotTour),
            getString(R.string.questBaggage),
            getString(R.string.questLookForAndBuy),
            getString(R.string.questWishings),
            getString(R.string.questWithPets),
            getString(R.string.questWhatDocuments),
            getString(R.string.questCheckIn),
            getString(R.string.questSendApplication),
            getString(R.string.questTips),
            getString(R.string.questPAcking),
            getString(R.string.questDifference)
        )

        answersArray = arrayOf(
            "Для большинства людей  туризм – это хобби. И счастлив  тот,   у кого хобби становится любимой работой!\n" +
                    "В Караване-12 работают счастливые люди,  и этим  счастьем  мы делимся с Вами, наши  дорогие туристы!\n" +
                    "Мы  обладаем большим опытом работы, постоянно развиваемся и осваиваем новые маршруты для Вас.\n" +
                    "У нас работают только высококвалифицированные менеджеры, которые внимательно отнесутся к Вашим пожеланиям, предоставят всю  информацию о турах в ту или иную страну, подберут нужный Вам маршрут и набор дополнительных услуг. Бронируем индивидуальные туры любой сложности, в любую точку мира!\n" +
                    "В нашей копилке – сложнейшие туры по Европе, морские круизы, в страны Азии.\n" +
                    "Наш слоган:  «Мы предлагаем только то, что видели сами!», был испытан на себе туристами нашего города, и не только. В течение  17  лет он является визитной карточкой нашего агентства!\n",
            "Данная информация так же будет указана в направленных маршрутных квитанциях. Если в Ваш тур не включён багаж, а только ручная кладь, то это, как правило, регулярный рейс.\n" +
                    "Если тур уже куплен, то добавить багаж можно по запросу через Туроператора, через авиакомпанию или уже по факту в аэропорту.\n" +
                    "Обращаем Ваше внимание, что авиакомпании Аэрофлот и Победа предусматривают только самостоятельное приобретение багажа.\n" +
                    "Если рейс чартерный, багаж и ручная кладь, как правило, всегда входят в стоимость, но может меняться в зависимости от авиакомпании. \n" +
                    "Если Вы хотите взять багаж, превышающий норму провоза багажа, условия провоза и доплаты за сверхнормативный багаж необходимо уточнять непосредственно у авиакомпании, которая осуществляет перевозку по выбранному Вами туру.",
            "Горящий тур — это путевка, которая по каким-либо причинам не была продана заранее.\n" +
                    "Ближе к датам тура, выкупленные туроператором места в самолёте или номера в определенном отеле начинают «гореть». Таким образом, путёвка продаётся по более низкой цене из-за сжатых сроков её реализации; чем ближе конец фиксированного срока, тем дешевле будет стоить тур. Так как количество мест ограничено, хорошие предложения выкупаются очень быстро.\n" +
                    "Приобрести горящие путёвки так же, как обычные, за несколько месяцев до вылета, невозможно.Оптимальное время поиска таких туров — с вылетом через 3-5 дней.",
            "Изучить туры вы можете на официальном сайте нашей компании либо заказать подбор в нашем приложении. После заполнения и отправки формы один из наших турагентов подберет подходящие вам туры и отправит на вашу почту",
            "Указать пожелания к туру можно в форме, где вы заполняете заявку на подбор тура. Все ваши пожелания будут учтены нашими турагентами!",
            "На сегодняшний день путешествия с домашними животными - достаточно частое явление. Также отелями и авиакомпаниями предоставляются услуги по перевозке и размещению животных.\n" +
                    "В то же время, для того, чтобы успешно отдохнуть с питомцем, необходимо уделить внимание на поиск соответствующего отеля и регистрацию питомца на борту самолёта.\n" +
                    "Ниже представлен перечень действий, который поможет подобрать наиболее подходящий для Вас вариант.\n" +
                    "Если вы собираетесь взять своего питомца в путешествие не забудьте указать его в пожеланиях при заполнении заявки на подбор тура.\n" +
                    "Указание животного в пожеланиях необходимо для того, чтобы мы смогли дополнительно согласовать провоз с авиакомпанией, ведь количество квот на провоз животного ограничено. Также необходимо понимать, что в провозе кошки может быть отказано, если на борту уже зарегистрирована собака (и наоборот).\n" +
                    "Ниже представлены общие основные требования к провозу домашнего животного:\n" +
                    "1. Домашнее животное должно иметь ветеринарный паспорт с ФИО владельца.\n" +
                    "2. Домашнее животное должно иметь отметку о вакцинации в соответствии с требованиями Россельхознадзора.\n" +
                    "3. Домашнее животное должно иметь отметку о клиническом осмотре государственным ветеринарным врачом.\n" +
                    "4. Животное может быть перевезено только с совершеннолетним владельцем.\n" +
                    "5. Домашнее животное должно иметь соответствующий тип контейнера, подходящий для конкретного вида и породы.\n" +
                    "В провозе животного может быть отказано по следующим причинам:\n" +
                    "1. Отсутствие ветеринарного паспорта и/или отметки о вакцинации.\n" +
                    "2. Отсутствие клинического осмотра перед вылетом.\n" +
                    "3. Несоответствие типа контейнера в отношении породы животного.\n" +
                    "4. Запрет на ввоз животного, принятый страной прилёта.\n" +
                    "5. Порода, вес, размер и другие параметры животного не соответствуют заявленным.\n" +
                    "6. Владелец животного не достиг совершеннолетнего возраста.\n" +
                    "7. На борту закончилась квота на провоз животных.\n" +
                    "8. Вид питомца не входит в утверждённый авиакомпанией перечень перевозимых животных.\n" +
                    "Точные требования к провозу животного, размеры и тип контейнера, стоимость провоза, а также порядок регистрации животного на борт самолёта необходимо уточнять самостоятельно на сайтах авиакомпаний и/или через контактные центры авиакомпаний и представительства в аэропортах.",
            "Заграничный паспорт — официальный документ, удостоверяющий личность гражданина при выезде за пределы и пребывании за пределами страны, а также при въезде на территорию государства из заграничной поездки. Обратите Внимание, что срок действия окончания паспорта должен соответствовать нормам принимающей стороны c начала/конца поездки.\n" +
                    "Туристический ваучер -  документ, устанавливающий права туриста на приобретенные услуги, входящие в состав тура, и подтверждающий факт их оказания.\n" +
                    "Страховка туроператора. Минимальная обязательная франшизная медицинская страховка от туроператора, от которой нельзя отказаться. Её особенность заключается в том, что необходимо платить за вызов врача и сохранять при этом документы об оплате услуг, чтобы, по возвращении, предоставить данный документ в страховую компанию для возврата денежных средств.\n" +
                    "Дополнительная медицинская страховка пользуется большим спросом у опытных туристов. Данная страховка оплачивается заранее, ориентировочная стоимость её составляет от 2,86 - 4 y.e. в день. Выгода заключается в том, что в случае необходимости обратиться к доктору Вам не нужно больше ни за что платить. Стоимость услуг вызова врача, госпитализации и т.д. уже включены в стоимость и Вам необходимо только предоставить номер страхового полиса, позвонив с страховую компанию.\n" +
                    "Виза - не входит в стоимость тура. Виза необходима для въезда в страну, с которой для граждан РФ установлен визовый режим. Стоимость визы зависит от туроператора, в поездку от которого Вы отправляетесь.По типу оформления, визы бывают традиционные (которые вклеиваются в загранпаспорт) и электронные (заполняются на соответствующем сайте страны временного пребывания).В некоторых странах визу возможно оформить по прилёте.\n",
            "Каждый отель устанавливает для себя время заезда и выезда гостей. Это позволяет производить уборку номеров и подготовку к заселению.\n" +
                    "Существуют стандартные правила заселения в отель. Если придерживаться их, можно заранее запланировать прибытие и получить максимум удовольствия от отдыха. Как правило, заселение осуществляется после 14:00 (на горнолыжных курортах - 15:00), а выезд из отеля – до 12:00 (на горнолыжных курортах - 10:00). Каждый отель на свое усмотрение может изменять это время. Узнать точное время заезда и отъезда можно, как правило, из листа бронирования.\n" +
                    "Если Вы приехали раньше, следует искать выход из ситуации на месте либо подождать несколько часов. Если ваш номер занят, и вы не хотите ждать, можно попросить идентичный номер. Возможно, вариант будет немного отличаться от забронированного (не будет вида на море, площадь номера будет меньше или больше). Если ожидания не избежать, можно с пользой провести время: погулять по территории, пойти на пляж. Багаж лучше оставить в специальной комнате (багажные помещения есть во многих отелях). Главное, не расстраиваться и пытаться найти выход на месте. Обычно в отелях идут навстречу пожеланиям и находят приемлемый для сторон вариант.\n" +
                    "Если отъезд состоится поздним вечером, предупредите администрацию об этом заранее. Если Ваш самолет прилетает ночью или поздно вечером, лучше заранее предупредить об этом. Обычно ресепшен работает круглосуточно, но в небольших отелях, как правило, есть только дежурный работник, который без проблем заселит Вас. Если Вы прибыли в отель ночью, Вам могут выдать ключи от номера, а регистрацию провести следующим утром. Отдельные отели (обычно, небольшие) требуют дополнительную плату за поздний заезд или выезд, поскольку работнику придется специально приезжать в свое личное время, чтобы заселить Вас.\n" +
                    "Услуга «late check-out» доступна во многих отелях. Это означает позднее выселение из гостиницы. Обычно она платная и позволяет оставаться в номере до трансфера в аэропорт. Иногда такая услуга входит в стоимость проживания.\n" +
                    "Если всё-таки отель не может предоставить услугу позднего выселения, Вы можете оставаться на территории или в холле отеля, на пляже.",
            "Для того, чтобы оставить заявку на подбор тура, вам нужно заполнить форму. Ответ придет на почту, которую вы указали при регистрации.",
            "1. Не берите с собой множество вещей.\n" +
                    "Они вам не понадобятся и значительно усложнят жизнь. Все эти чемоданы придется таскать за собой, а в лучшем случае вы используете только половину взятых вещей.\n" +
                    "2. Берите с собой только удобные и практичные вещи.\n" +
                    "3. Составьте перед поездкой примерный план путешествия.\n" +
                    "4. Выучите пару фраз на языке страны\n" +
                    "5. Не стремитесь увидеть всё сразу\nЕсли вы хотите действительно насладиться путешествием и расслабиться, не старайтесь успеть посмотреть абсолютно все. Остановите свой выбор на одном городе и не спеша его изучайте.\n" +
                    "6. Хорошо питайтесь\n"+
                    "7. Отведайте местную кухню\n" +
                    "8. Фотографируйте в меру\nФотоаппарат в путешествии необходим, но при этом стоит изредка выпускать его из рук и просто наслаждаться открывающимися пейзажами.\n"+
                    "9. Возьмите удобный рюкзак\n"+
                    "10. Забудьте о проблемах\nОставьте все проблемы и заботы дома, в путешествии не время забивать себе этим голову.",
            "Любая поездка в отпуск сопровождается сборами чемодана, которые нередко приносят дискомфорт. Прежде чем отправиться в путь, нужно хорошенько всё продумать: собрать всё нужное и оставить дома ненужное. Мы составили универсальный список вещей, которые вам точно пригодятся в поездке.\n"+
                    "1. Деньги и документы: загранпаспорт/паспорт, медстраховка, билеты на самолет/поезд, наличка, бронь отеля\n"+
                    "2. Одежда\nПрежде чем начать собирать одежду и обувь, продумайте, чем вы будете заниматься на отдыхе. Некоторые вещи отфильтруются сами собой.\n"+
                    "3. Косметика или предметы личной гигены: зубная щетка и паста, шампунь и гель для душа, бритвенный станок (его кладите только в чемодан!), уходовая косметика и обязательно берите с собой солнцезащитный крем.\n"+
                    "4. Лекарства: обезбаливающие, жаропонижающие, антигистаминные, антисептики и пластыри, средства при отравлении и расстройствах желудка.\nЕсли у вас есть какие-то заболевания, в первую очередь соберите лекарства, которые будут вам жизненно необходимы в поездке.\n"+
                    "5. Техника: смартфоны, наушники, портативная зарядка, фотоаппарат.\n"+
                    "Чтобы быстро и компактно сложить вещи предлагаем вам несколько советов!\n"+
                    "Одежду лучше не складывать, а скручивать. Это сэкономит место.\nУбирайте обувь в специальные мешки.\nКрем, зубную пасту и жидкие средства положите в отдельную косметичку или в герметичный пакет.\nЛёгкие вещи кладите наверх, обувь – вниз, а пустоту между вещами заполняйте мелкими предметами.\n"+
                    "Теперь вы точно готовы отправиться в отпуск. Приятного путешествия!",
            "Чартерные рейсы - это рейсы, которые происходят по заказу туроператора на определенное направление на основе чартерного договора между ним и авиакомпанией.\n"+
                    "Регулярные рейсы – это рейсы, которые выполняются круглогодично и утверждаются межправительственными соглашениями.\n"+
                    "Их расписание составляется на год вперед и изменения в него вносятся только в редких случаях по особым причинам, угрожающим безопасности пассажиров (погодные условия, природные катаклизмы, военно-политическая обстановка и т.д.).\n"+
                    "Если перелет осуществляется чартерным рейсом, то время вылета и аэропорт могут быть изменены.\n"+
                    "Если перелет осуществляется регулярным рейсом, то билеты будут выписаны и туроператор уже не заменит полетную программу. Изменение времени вылета в данном случае встречается намного реже.\n"+
                    "Время вылета по таким рейсам Вы знаете сразу на момент оплаты тура."
        )
    }

}
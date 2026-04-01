package com.example.btl_hethongdatsancaulong.controllers.customer;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.btl_hethongdatsancaulong.R;
import com.example.btl_hethongdatsancaulong.adapters.FeaturedAdapter;
import com.example.btl_hethongdatsancaulong.databinding.ActivityFeaturedBinding;
import com.example.btl_hethongdatsancaulong.models.FeaturedNews;
import java.util.ArrayList;
import java.util.List;

public class FeaturedActivity extends AppCompatActivity {

    private ActivityFeaturedBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityFeaturedBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnBackFeatured.setOnClickListener(v -> finish());

        binding.navHome.setOnClickListener(v -> {
            startActivity(new Intent(FeaturedActivity.this, MainHomeActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        });
        binding.navMap.setOnClickListener(v -> {
            startActivity(new Intent(FeaturedActivity.this, MapActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        });
        binding.navAccount.setOnClickListener(v -> {
            startActivity(new Intent(FeaturedActivity.this, AccountActivity.class).addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION));
            finish();
        });
        // 1. Tạo dữ liệu mẫu bài báo
        List<FeaturedNews> newsList = new ArrayList<>();
        newsList.add(new FeaturedNews(
                "Giải Đấu Cầu Lông Hàng Năm: Đấu trường của các ngôi sao",
                "Hệ thống các giải đấu BWF\n" +
                        "Liên đoàn Cầu lông Thế giới (BWF) là tổ chức quản lý và điều hành các giải đấu cầu lông trên toàn cầu. BWF tổ chức một hệ thống các giải đấu đa dạng, từ những giải đấu lớn mang tính quốc tế đến các giải đấu cấp khu vực, tạo nên một sân chơi hấp dẫn cho các vận động viên cầu lông trên khắp thế giới.\n" +
                        "\n" +
                        "Ý nghĩa của các giải đấu BWF\n" +
                        "\n" +
                        "Các giải đấu BWF đóng vai trò quan trọng trong việc\n" +
                        "\n" +
                        "Phát triển cầu lông: Tạo ra một môi trường cạnh tranh lành mạnh để các vận động viên nâng cao kỹ năng và thể lực.\n" +
                        "Xây dựng cộng đồng cầu lông: Kết nối các vận động viên, huấn luyện viên và người hâm mộ trên toàn thế giới.\n" +
                        "Giải đấu còn Thu hút sự quan tâm của công chúng và truyền bá môn thể thao này đến nhiều quốc gia.\n" +
                        "\n" +
                        "Hệ thống giải đấu của BWF được chia thành nhiều cấp độ khác nhau, từ thấp đến cao, tạo nên một con đường rõ ràng cho sự phát triển của các vận động viên. Một số cấp độ chính bao gồm:\n" +
                        "\n" +
                        "Giải Super 1000, Super 750, Super 500, Super 300 và BWF Tour Super 100: Đây là những giải đấu cao cấp nhất trong hệ thống BWF, thu hút sự tham gia của các vận động viên hàng đầu thế giới. Các giải đấu này mang đến những trận đấu đỉnh cao và cơ hội giành được số điểm thưởng cao để cải thiện thứ hạng thế giới.\n" +
                        "Giải BWF World Tour Challengers: Đây là cấp độ tiếp theo, tạo điều kiện cho các vận động viên trẻ và những vận động viên đang lên tích lũy kinh nghiệm thi đấu quốc tế.\n" +
                        "Giải BWF International Challenge: Đây là cấp độ thấp hơn, dành cho các vận động viên trẻ và những vận động viên mới bắt đầu sự nghiệp chuyên nghiệp.\n" +
                        "Thomas & Uber Cup: TotalEnergies BWF Thomas & Uber Cup Finals 2021 được biết đến là giải cầu lông Vô địch đồng đội Nam & Đồng đội Nữ diễn ra 2 năm một lần với sự góp mặt của 16 cường quốc cầu lông trên Thế Giới.\n" +
                        "Sudirman Cup: Sudirman Cup là một cuộc thi cầu lông đồng đội hỗn hợp quốc tế được tranh tài bởi các quốc gia thành viên của Liên đoàn cầu lông thế giới, cơ quan quản lý toàn cầu của môn thể thao này. Chức vô địch được trao hai năm một lần kể từ giải đấu khai mạc năm 1989.\n" +
                        "Trong đó có 2 giải đấu đặc biệt nhất – Hai giải đấu được mong đợi nhất năm\n" +
                        "1. Olympic Cầu Lông: Đỉnh Cao Của Môn Thể Thao\n" +
                        "\n" +
                        "Là sự kiện thể thao lớn nhất, Olympic diễn ra mỗi bốn năm một lần và quy tụ các vận động viên cầu lông xuất sắc nhất từ khắp nơi trên thế giới.\n" +
                        "Olympic: là một trong những sự kiện thể thao lớn nhất và danh giá nhất thế giới, quy tụ những tay vợt hàng đầu đến từ các quốc gia khác nhau. Đây là sân chơi nơi các vận động viên thể hiện tài năng, kỹ thuật và tinh thần thi đấu cao thượng để tranh tài giành những tấm huy chương quý giá về cho quốc gia\n" +
                        "\n" +
                        "Olympic Cầu Lông\n" +
                        "2. BWF World Championships: Đấu Trường Ngôi Vương Toàn Cầu\n" +
                        "\n" +
                        "Chinh Phục Bảng Xếp Hạng: Đây là cơ hội để các vận động viên chứng minh kỹ năng và phong cách cá nhân, đồng thời ảnh hưởng đến bảng xếp hạng cầu lông thế giới.\n" +
                        "Giải vô địch cầu lông thế giới BWF World Championships: Đây là giải đấu lớn nhất và uy tín nhất trong làng cầu lông. Giải đấu này quy tụ những tài năng hàng đầu thế giới và là mục tiêu của mọi vận động viên cầu lông. Vận động viên vượt qua các tuyển thủ chuyên nghiệp khác để giành được huy chương vàng trong giải này. Để thuận tiện cho các tay vợt và không làm ảnh hưởng đến lịch trình của họ, giải đấu sẽ không trùng với thời gian tổ chức Thế vận hội mùa hè, tức là 3 năm liên tục và 1 năm không trùng với Olympic.\n" +
                        "II. Giải Đấu Cấp Độ Châu Lục, Quốc gia và địa phương\n" +
                        "Asian Games\n" +
                        "\n" +
                        "Asian Games (Đại hội Thể thao Châu Á) là một sự kiện thể thao đa môn lớn nhất châu Á, được tổ chức bởi Ủy ban Olympic châu Á (OCA) và diễn ra mỗi 4 năm. Cầu lông đã trở thành một trong những môn thể thao chính thức tại Asian Games từ năm 1962. Tại Asian Games, cầu lông là một trong những môn thể thao thu hút sự chú ý lớn nhất. Các tay vợt hàng đầu từ khắp nơi trong khu vực tranh tài ở các nội dung đơn nam, đơn nữ, đôi nam, đôi nữ, và đôi hỗn hợp. Asian Games không chỉ là cơ hội để các tay vợt thể hiện tài năng mà còn là một sân chơi quan trọng giúp các vận động viên giành điểm số và uy tín trong cộng đồng thể thao châu Á. Việc giành huy chương tại Asian Games có thể tạo đà phát triển cho sự nghiệp thể thao của các tay vợt.\n" +
                        "\n" +
                        "\n" +
                        "Asian Games (Đại hội Thể thao Châu Á)\n" +
                        "European Badminton Championships\n" +
                        "European Badminton Championships là giải đấu cầu lông quan trọng nhất ở châu Âu, thu hút những tay vợt hàng đầu từ khắp lục địa. Được tổ chức bởi Liên đoàn Cầu lông châu Âu (Badminton Europe), giải đấu này được tổ chức hàng năm và được coi là một trong những giải đấu danh giá nhất trong khu vực. Các tay vợt tranh tài ở các nội dung đơn nam, đơn nữ, đôi nam và đôi nữ, cùng với đôi hỗn hợp. Đây là cơ hội để các tay vợt châu Âu chứng tỏ tài năng và giành suất tham dự các giải đấu lớn khác.\n" +
                        "\n" +
                        "National Badminton League (NBL) – Anh Quốc\n" +
                        "National Badminton League (NBL) là giải đấu cầu lông nổi bật tại Anh Quốc, đóng vai trò quan trọng trong việc phát triển môn thể thao này tại quốc gia này. Được tổ chức hàng năm, NBL quy tụ các đội tuyển từ khắp nơi trong nước để thi đấu trong một mùa giải cạnh tranh. Với format đội tuyển, NBL mang lại cơ hội cho các tay vợt ở nhiều cấp độ khác nhau để thể hiện kỹ năng và tạo cơ hội cho các tài năng mới nổi. Đây cũng là nền tảng quan trọng giúp tăng cường sự phát triển của cầu lông tại Anh và xây dựng cộng đồng yêu thích môn thể thao này.\n" +
                        "\n" +
                        "Hệ Thống Các Giải Đấu Cầu Lông Quan Trọng\n" +
                        "Các giải đấu như All England Open, French Open, Malaysia Masters, Denmark Open, Japan Open, và China Open là những sự kiện không thể bỏ qua trong lịch thi đấu cầu lông quốc tế. Mỗi giải đấu đều có đặc điểm riêng và sức hút riêng biệt:\n" +
                        "\n" +
                        "All England Open: Được coi là giải đấu lâu đời và danh giá nhất trong làng cầu lông, All England Open diễn ra tại Birmingham, Anh Quốc. Từ khi được tổ chức lần đầu vào năm 1899, giải đấu này đã trở thành mơ ước của nhiều tay vợt để giành được danh hiệu này. Sự kiện này thường thu hút sự tham gia của các tay vợt hàng đầu thế giới và luôn tạo ra những trận đấu hấp dẫn.\n" +
                        "French Open: Diễn ra tại Paris, Pháp, French Open là một phần của hệ thống giải đấu Super 750 trong BWF World Tour. Đây là cơ hội để các tay vợt chứng tỏ khả năng trên sân đấu và cạnh tranh với những đối thủ mạnh mẽ từ khắp nơi trên thế giới.\n" +
                        "Malaysia Masters: Malaysia Masters là một giải đấu quan trọng trong BWF World Tour, diễn ra tại Kuala Lumpur, Malaysia. Giải đấu này thường thu hút sự tham gia của nhiều tay vợt hàng đầu châu Á, và là cơ hội để các tay vợt Malaysia thể hiện tài năng trên sân nhà.\n" +
                        "Denmark Open: Denmark Open được tổ chức tại Odense, Đan Mạch, và là một phần của hệ thống giải đấu Super 750. Đây là một trong những giải đấu quan trọng ở châu Âu, thu hút sự tham gia của nhiều tay vợt hàng đầu từ khắp nơi trên thế giới và thường xuyên tạo ra những trận đấu kịch tính.\n" +
                        "Japan Open: Japan Open diễn ra tại Tokyo, Nhật Bản, và là một phần của BWF World Tour. Giải đấu này không chỉ nổi bật vì chất lượng cao của các tay vợt tham dự mà còn vì sự tổ chức chuyên nghiệp và các điều kiện thi đấu tuyệt vời.\n" +
                        "China Open: China Open là một trong những giải đấu quan trọng nhất trong hệ thống giải đấu BWF World Tour, diễn ra tại các thành phố lớn của Trung Quốc như Beijing hoặc Shanghai. Với sự tham gia của các tay vợt hàng đầu và sự tổ chức chuyên nghiệp, giải đấu này luôn là điểm nhấn trong lịch thi đấu cầu lông quốc tế.\n" +
                        "Các giải đấu lớn tại Việt Nam\n" +
                        "Tại Việt Nam, hai giải cầu lông lớn nhất và nổi bật nhất là Vietnam International Challenge và Vietnam Open. Đây là hai giải đấu quan trọng trong hệ thống các giải cầu lông quốc tế, thu hút nhiều vận động viên nổi tiếng và là cơ hội để các tay vợt nâng cao thứ hạng trên bảng xếp hạng thế giới. Dưới đây là thông tin chi tiết về từng giải:\n" +
                        "\n" +
                        "1. Vietnam International Challenge\n" +
                        "Mức độ giải đấu: Đây là một giải đấu nằm trong hệ thống giải International Challenge của Liên đoàn Cầu lông Thế giới (BWF). Các giải đấu này có cấp độ thấp hơn so với các giải Super Series hay World Tour nhưng vẫn thu hút sự chú ý từ các tay vợt quốc tế.\n" +
                        "Tầm quan trọng: Giải Vietnam International Challenge thường là cơ hội tốt cho các tay vợt trẻ và chưa có thứ hạng cao trên thế giới để thi đấu và cọ xát với các đối thủ quốc tế. Nó cũng là cơ hội để các vận động viên Việt Nam thử sức và có thêm cơ hội chiến thắng các danh hiệu quốc tế.\n" +
                        "Giải Đấu Cầu Lông Hàng\n" +
                        "Giải Đấu Cầu Lông Hàng\n" +
                        "2. Vietnam Open\n" +
                        "Mức độ giải đấu: Vietnam Open là một giải đấu trong hệ thống giải đấu của BWF, được xếp hạng cao hơn so với các giải International Challenge, thuộc vào nhóm International Series. Điều này có nghĩa là nó có tầm quan trọng và quy mô lớn hơn so với Vietnam International Challenge.\n" +
                        "Tầm quan trọng: Vietnam Open thu hút nhiều tay vợt hàng đầu từ các quốc gia khác nhau và có sự cạnh tranh cao hơn. Đây là cơ hội để các vận động viên nâng cao thứ hạng của mình trong bảng xếp hạng BWF và tích lũy điểm số quan trọng cho các giải đấu lớn hơn.\n" +
                        "Cả hai giải đấu đều đóng vai trò quan trọng trong việc phát triển cầu lông tại Việt Nam và góp phần tạo cơ hội cho các tay vợt quốc gia thi đấu với các đối thủ quốc tế. Trong khi Vietnam International Challenge thường có quy mô nhỏ hơn và là cơ hội cho các tay vợt mới nổi, Vietnam Open có tầm quan trọng và quy mô lớn hơn, thu hút nhiều tay vợt hàng đầu và có sự cạnh tranh cao hơn.\n" +
                        "\n" +
                        "Lịch cầu lông quốc tế năm 2024\n" +
                        "Lịch cầu lông quốc tế năm 2024 hứa hẹn sẽ diễn ra đầy hấp dẫn với các sự kiện quan trọng như Malaysia Masters, Thế vận hội Paris 2024 và BWF World Tour Finals. Đây là thời điểm lý tưởng để các tay vợt săn lùng điểm xếp hạng và chứng minh khả năng của mình trên đấu trường quốc tế. Cùng theo dõi và cổ vũ cho những chiến binh cầu lông trong những mùa giải đầy kịch tính này nhé.",
                R.drawable.feat1,
                "06/09/2025"));

        newsList.add(new FeaturedNews(
                "Tổng hợp 3 giải cầu lông nổi tiếng toàn thế giới",
                "Môn cầu lông ngày càng được nhiều người quan tâm, khẳng định vị thế của mình trên trường quốc tế. Cũng từ đó các giải đấu cầu lông quốc tế cũng ngày một nhiều. Hãy cùng ShopVNB điểm danh những giải đấu nổi tiếng nhất trong lông người hâm mộ nhé.\n" +
                        "\n" +
                        "1. Hệ thống giải cầu lông thế giới Grand Prix và Grand Prix Gold\n" +
                        "Giải đấu Grand Prix và Grand Prix Gold nổi tiếng thế giới bởi quy mô tổ chức. Giải đấu cũng giữ một vai trò quan trọng đối với làng cầu lông thế giới. Hệ thống giải bao gồm rất nhiều giải con được phân bố tổ chức khắp toàn cầu. Nhiều vợt thủ kỳ cựu, xuất sắc đều góp mặt ở giải này. Họ đều có thành tích trong bảng xếp hạng cầu lông thế giới của liên liên đoàn.\n" +
                        "\n" +
                        "giải cầu lông thế giới Grand Prix và Grand Prix Gold\n" +
                        "\n" +
                        "giải cầu lông thế giới Grand Prix và Grand Prix Gold\n" +
                        "\n" +
                        "Giải thưởng được tổ chức thường niên từ năm 2007, giải được ký kết bởi BWF, dựa trên nỗ lực tìm ra cây vợt xuất sắc nhất giải để trao số tiền thưởng 50.000$ hoặc hơn (đối với Grand Prix), và lên đến 120 000$ hoặc hơn (dành cho Grand Prix Golden).\n" +
                        "\n" +
                        "2. Hệ thống giải cầu lông thế giới nổi tiếng đấu Super Series\n" +
                        "Giải cầu lông thế giới nổi tiếng đấu Super Series không còn xa lạ đối với các vận động viên tên tuổi.\n" +
                        "\n" +
                        "Giải thưởng nổi tiếng với quy mô tổ chức còn lớn hơn Giải đấu Grand Prix và Grand Prix Gold. Mùa giải “Siêu cấp” trình làng vào ngày 14 tháng 12 năm 2006, được tổ chức thi đấu vào năm 2007 Hiệp hội cầu lông thế giới đã công nhận Super Series là một giải thi đấu chính thức, chia ra hai cấp độ để phân loại là: Super Series Premier và Super Series.\n" +
                        "\n" +
                        "Người duy nhất hoàn thành toàn bộ cúp của giải này không ai khác là LinDan, vợt thủ số 1 TRUNG QUỐC và là người dành nhiều giải cầu lông nhất đối với làng cầu lông thế giới.\n" +
                        "\n" +
                        "giải cầu lông thế giới nổi tiếng đấu Super Seriesgiải cầu lông thế giới nổi tiếng đấu Super Series\n" +
                        "\n" +
                        "giải cầu lông thế giới nổi tiếng đấu Super Series\n" +
                        "\n" +
                        "3. Hệ thống Giải cầu lông thế giới BWF World Championships\n" +
                        "Cái tên sừng sỏ và chức vô địch là thứ vợt thủ nào cũng mong muốn đạt được. Giải vô địch cầu lông thế giới BWF World Championships là giải cầu lông mơ ước của nhiều tuyển thủ chuyên nghiệp. Bởi đây là giải tầm cỡ nhất trong làng cầu lông.Đa số các vận động viên tham gia giải đấu này trước hết với mong muốn gặp gỡ các đối thủ mạnh để có thêm kinh nghiệm, mục đích kế tiếp là tích lũy điểm thưởng để tăng hạng.\n" +
                        "\n" +
                        "Giải cầu lông thế giới BWF World Championships\n" +
                        "\n" +
                        "Giải cầu lông thế giới BWF World Championships\n" +
                        "\n" +
                        "Giải cầu lông BWF World Championships được thành lập vào năm 1977, được tổ chức 3 năm một lần. Sau này chỉ tổ chức hai năm một lần vào các năm lẻ. Vận động viên giỏi nhất, vượt qua các tuyển thủ chuyên nghiệp khác sẽ giành được huy chương vàng danh giá. Giải thưởng danh giá này không đi kèm bất kỳ khoản tiền thưởng nào. Để thuận tiện và không làm ảnh hưởng đến lịch trình của các tay vợt, giải đấu sẽ không diễn ra vào thời điểm trùng với thời gian tổ chức Thế vận hội mùa hè.\n" +
                        "\n" +
                        "Trên đây là tổng hợp 3 giải cầu lông quốc tế được nhiều người chơi cầu lông săn đón. Cảm ơn bạn đã theo dõi ShopVNB hay theo dõi chúng mình thường xuyên để biết thêm nhiều điều thú vị nhé.",
                R.drawable.feat2,
                "31/08/2025"));

        newsList.add(new FeaturedNews(
                "Top các giải cầu lông lớn nhất thế giới",
                "Cầu lông là một trong những môn thể thao được yêu thích và phổ biến trên toàn thế giới, thu hút hàng triệu người chơi và người hâm mộ. Trong những giải cầu lông lớn nhất thế giới không chỉ là sân chơi để các vận động viên hàng đầu thể hiện tài năng, mà còn là nơi ghi dấu những khoảnh khắc lịch sử, tranh tài đầy kịch tính và cảm xúc. Hãy cùng HVShop khám phá sức hấp dẫn và tầm quan trọng của những giải cầu lông hàng đầu thế giới trong bài viết dưới đây.\n" +
                        "\n" +
                        "Mục Lục\n" +
                        "Giải cầu lông vô địch thế giới (BWF World Championships)\n" +
                        "Hệ thống giải đấu Super Series\n" +
                        "Cup Thomas và Uber (Thomas Cup và Uber Cup)\n" +
                        "Giải đấu cầu lông Olympic\n" +
                        "Giải cầu lông vô địch thế giới (BWF World Championships)\n" +
                        "Giải vô địch cầu lông thế giới (BWF World Championships) là một sự kiện đỉnh cao trong làng cầu lông quốc tế, được tổ chức bởi Liên đoàn Cầu lông Thế giới (BWF). Ra đời vào năm 1977, giải cầu lông lớn nhất thế giới này đã trở thành đấu trường danh giá, nơi quy tụ những tay vợt xuất sắc nhất từ khắp nơi trên thế giới để tranh tài và khẳng định vị trí của mình.\n" +
                        "\n" +
                        "Giải cầu lông vô địch thế giới (BWF World Championships)\n" +
                        "Giải cầu lông vô địch thế giới (BWF World Championships)\n" +
                        "Điều đặc biệt của BWF World Championships là không có phần thưởng tiền mặt, mà phần thưởng lớn nhất là danh hiệu vô địch thế giới, cùng tấm huy chương vàng và điểm xếp hạng quý giá, giúp nâng cao vị thế cho các vận động viên.\n" +
                        "\n" +
                        "Khi mới thành lập vào năm 1977, giải đấu được tổ chức 3 năm một lần. Sau này được tổ chức thường niên trừ năm có giải Olympic và bao gồm năm nội dung thi đấu: đơn nam, đơn nữ, đôi nam, đôi nữ và đôi nam nữ.\n" +
                        "\n" +
                        "Đây là sân chơi mang tính cạnh tranh khốc liệt, nơi khán giả có thể chứng kiến những trận đấu kịch tính, pha cầu đẹp mắt, và những màn trình diễn đầy ấn tượng từ các tay vợt hàng đầu. Những huyền thoại như Lin Dan, Lee Chong Wei, và Carolina Marín đã làm nên tên tuổi của mình tại đây, biến giải đấu thành biểu tượng cho sự phát triển của cầu lông.\n" +
                        "\n" +
                        "BWF World Championships không chỉ là sự kiện thể thao mà còn là lễ hội gắn kết cộng đồng cầu lông quốc tế. Khán giả từ khắp nơi đổ về để cổ vũ, tạo nên bầu không khí sôi động và giàu cảm xúc. Giải đấu này không ngừng góp phần thúc đẩy sự phổ biến và phát triển bền vững của cầu lông trên toàn cầu, là giấc mơ và mục tiêu của nhiều tay vợt tài năng.\n" +
                        "\n" +
                        "Hệ thống giải đấu Super Series\n" +
                        "Super Series là một trong những hệ thống giải cầu lông lớn nhất thế giới bất kì lông thủ nào cũng từng nghe qua. Hệ thống giải đấu Super Series là một chuỗi các giải cầu lông quốc tế được tổ chức bởi Liên đoàn Cầu lông Thế giới (BWF), với mục tiêu nâng cao chất lượng và uy tín của môn thể thao này trên toàn cầu.\n" +
                        "\n" +
                        "Hệ thống giải đấu Super Series\n" +
                        "Hệ thống giải đấu Super Series\n" +
                        "Vào năm 2007, Super Series được Hiệp hội cầu lông thế giới công nhận là một giải thi đấu chính thức và đưa phân loại thành hai cấp độ là bao gồm Super Series Premier và Super Series.\n" +
                        "\n" +
                        "Các giải Super Series Premier là các giải đấu quan trọng hơn, với tổng giá trị giải thưởng cao hơn và điểm xếp hạng cũng lớn hơn so với các giải Super Series thông thường. Những giải đấu này diễn ra ở nhiều quốc gia, tạo cơ hội cho các tay vợt thi đấu tại những đấu trường quốc tế lớn, từ các cường quốc cầu lông như Trung Quốc, Đan Mạch, Hàn Quốc, đến các quốc gia khác trên khắp châu Á và châu Âu.\n" +
                        "\n" +
                        "Chính vì vậy, giải đấu này quy tụ các tay vợt xuất sắc nhất từ khắp nơi trên thế giới. Hệ thống này giúp tạo ra một lịch thi đấu chặt chẽ, hấp dẫn, đồng thời mang đến cơ hội để các vận động viên tích lũy điểm xếp hạng và cạnh tranh danh hiệu trong suốt mùa giải.\n" +
                        "\n" +
                        "Một trong những đặc điểm nổi bật của hệ thống giải đấu Super Series là việc có những trận đấu giữa các tay vợt hàng đầu, tạo ra sự cạnh tranh gay gắt và kịch tính. Các vận động viên phải thể hiện khả năng vượt trội về kỹ thuật, chiến thuật và thể lực để giành chiến thắng và tích lũy điểm xếp hạng.\n" +
                        "\n" +
                        "Với quy mô và tầm quan trọng của mình, hệ thống giải đấu Super Series đã góp phần không nhỏ vào việc nâng cao chất lượng cầu lông và phát triển môn thể thao này trên toàn thế giới.\n" +
                        "\n" +
                        "Cup Thomas và Uber (Thomas Cup và Uber Cup)\n" +
                        "Thomas Cup & Uber Cup là giải đấu cầu lông quốc tế danh giá, được tổ chức định kỳ bởi Liên đoàn Cầu lông Thế giới (BWF), và là những giải đấu đồng đội quan trọng nhất trong môn thể thao cầu lông.\n" +
                        "\n" +
                        "Cup Thomas và Uber (Thomas Cup & Uber Cup)\n" +
                        "Cup Thomas và Uber (Thomas Cup & Uber Cup)\n" +
                        "Thomas Cup & Uber Cup được tổ chức với chu kì hai năm một lần. Đây là một cuộc thi giữa các đội tuyển nam và các tay vợt nữ tranh tài để giành chiếc cúp danh giá. Cup Thomas và Uber không chỉ là một cơ hội tuyệt vời để các tay vợt thể hiện tài năng mà còn là dịp để các quốc gia tăng cường sự phát triển của bộ môn vũ cầu.\n" +
                        "\n" +
                        "Giải đấu này rất quan trọng đối với sự phát triển của cầu lông toàn cầu và có ảnh hưởng sâu rộng đến việc nâng cao chất lượng và phổ biến môn thể thao này. Những đội tuyển chiến thắng Thomas Cup & Uber Cup không chỉ giành được danh hiệu quốc tế mà còn củng cố vị thế của quốc gia mình trong làng cầu lông thế giới.\n" +
                        "\n" +
                        "Giải đấu cầu lông Olympic\n" +
                        "Giải đấu cầu lông Olympic là một trong những sự kiện thể thao lớn và uy tín nhất trên thế giới, được tổ chức mỗi bốn năm trong khuôn khổ Thế vận hội (Olympics). Cầu lông lần đầu tiên được đưa vào thi đấu Olympic vào năm 1992 tại Barcelona (Tây Ban Nha) và nhanh chóng trở thành một môn thể thao thu hút sự chú ý lớn của khán giả toàn cầu.\n" +
                        "\n" +
                        "Giải đấu cầu lông Olympic\n" +
                        "Giải đấu cầu lông Olympic\n" +
                        "Giải cầu lông lớn nhất thế giới này tổ chức với các nội dung bao gồm: Đơn nam, đơn nữ, đôi nam, đôi nữ. đôi nam nữ. Các vận động viên tham gia giải đấu Olympic phải vượt qua một loạt các vòng đấu loại và đối mặt với những tay vợt xuất sắc nhất thế giới để giành lấy huy chương vàng danh giá.\n" +
                        "\n" +
                        "Với sự tham gia của những tên tuổi hàng đầu trong làng cầu lông, giải đấu Olympic không chỉ là cuộc tranh tài giữa các vận động viên mà còn là cuộc đấu trí, thể hiện sự kết hợp giữa kỹ thuật, chiến thuật và thể lực.\n" +
                        "\n" +
                        "Đặc biệt, giải đấu cầu lông Olympic mang đến không chỉ giá trị về mặt thể thao mà còn là dịp để các quốc gia thể hiện sức mạnh và niềm tự hào. Các tay vợt như Lin Dan (Trung Quốc), Lee Chong Wei (Malaysia), Carolina Marín (Tây Ban Nha), và P.V. Sindhu (Ấn Độ) đều đã ghi dấu ấn mạnh mẽ tại các kỳ Olympic, góp phần nâng cao tầm ảnh hưởng của môn cầu lông trên đấu trường quốc tế.\n" +
                        "\n" +
                        "Thành công ở giải đấu này có thể thay đổi cả sự nghiệp của các vận động viên, vì chiếc huy chương Olympic không chỉ là minh chứng cho tài năng mà còn là đỉnh cao của sự nghiệp thể thao. Cầu lông Olympic không chỉ là giải đấu quan trọng mà còn là một phần không thể thiếu trong việc phát triển và quảng bá môn thể thao này đến với người hâm mộ trên toàn thế giới.\n" +
                        "\n" +
                        "Trên đây là tất cả những chia sẻ về các giải cầu lông lớn nhất thế giới. Hy vọng qua đó sẽ mang đến cho bạn những thông tin hữu ích và dễ dàng theo dõi những trận đấu cầu lông quan trọng.",
                R.drawable.feat3,
                "05/12/2025"));

        // 2. Thiết lập Adapter
        FeaturedAdapter adapter = new FeaturedAdapter(newsList, news -> {
            // Khi ấn "Xem chi tiết" -> Sang màn hình nội dung
            Intent intent = new Intent(this, FeaturedDetailActivity.class);
            intent.putExtra("TITLE", news.getTitle());
            intent.putExtra("CONTENT", news.getContent());
            intent.putExtra("DATE", news.getDate());
            startActivity(intent);
        });

        binding.recyclerViewFeatured.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerViewFeatured.setAdapter(adapter);
    }
}
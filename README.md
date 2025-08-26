# 📱 Mobile Intern Test – Address Search App

Ứng dụng Android demo cho bài test **Mobile Intern**: tìm kiếm địa chỉ theo từ khoá, hiển thị danh sách kết quả, chạm vào kết quả để mở Google Maps chỉ đường.

---

## ✨ Tính năng chính
- 🔍 **Search địa chỉ** bằng API [LocationIQ](https://locationiq.com/).
- ⏳ **Debounce 1 giây** khi nhập để tránh gọi API liên tục.
- 📋 Hiển thị **danh sách kết quả** (RecyclerView).
- ✨ **Highlight từ khoá** trong kết quả.
- 🗺️ **Mở Google Maps** khi chạm vào kết quả.

---

## 📸 Demo

### Video
<video src="docs/demo.mp4" controls="controls" width="400"></video>

*(Nếu không xem được video trực tiếp, mở file [`docs/demo.mp4`](docs/demo.mp4))*

---

## 🛠️ Công nghệ sử dụng
- **Ngôn ngữ**: Java (Android)
- **Kiến trúc**: MVVM + Repository
- **UI**: RecyclerView, ConstraintLayout, ViewBinding
- **Networking**: Retrofit2 + OkHttp3 + Gson
- **Reactive**: RxJava3 (debounce 1s)

---


## 👨‍💻 TT
- **Tên**: Ngô Hải Nam  
- **Email**: ngohainam1311@gmail.com  
- **GitHub Repo**: [TestTT](https://github.com/namnhph37478/TestTT)

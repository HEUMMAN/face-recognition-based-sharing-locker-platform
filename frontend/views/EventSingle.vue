<template>
  <div class="event-single">
    <section class="hero is-primary">
      <div class="hero-body">
        <div class="container">
          <h1 class="title">{{ event.name }}</h1>
          <h2 class="subtitle">
            <strong>Date:</strong>
            {{ event.date }}
            <br />
            <strong>Time:</strong>
            {{ event.time }}
          </h2>
        </div>
      </div>
    </section>
    <section class="event-content">
      <div class="container">
        <p class="is-size-4 description">{{ event.description }}</p>
        <p class="is-size-5">
          <strong>Location:</strong>
          {{ event.location }}
        </p>
        <p class="is-size-5">
          <strong>Category:</strong>
          {{ event.category }}
        </p>
        <div class="event-images columns is-multiline has-text-centered">
          <div v-for="image in event.images" :key="image.id" class="column is-one-third">
            <img :src="`${image}`" :alt="`${event.name}`" />
          </div>
        </div>
      </div>
    </section>
  </div>
</template>
<script>
export default {
  name: "EventSingle",
  data() {
    return {
      events: [
        {
          id: 1,
          name: "산융4층 사물함",
          category: "얼굴인식사물함",
          description:
            "1~3번 사물함",
          featuredImage: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
          images: [
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500"
          ],
          location: "산융4층",
          date: "2021-03-31",
          time: "11:30"
        },
        {
          id: 2,
          name: "정왕역 사물함",
          category: "얼굴인식사물함",
          description:
            "1번 사물함",
          featuredImage: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500",
          images: ["https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQLL84svmqTHsohu2Bx_t6ZRizodTiDxweGzA&usqp=CAU/500/500"],
          location: "정왕역 1층 출구 옆",
          date: "2021-04-31",
          time: "12:00"
        }
      ],
      event: {}
    };
  },
  created() {
    const ID = Number(this.$route.params.id);
    let event = this.events.find(event => event.id === ID);
    this.event = event;
  }
};
</script>
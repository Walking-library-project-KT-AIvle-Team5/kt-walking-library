<template>
    <div>
        <String
            label="Title"
            v-model="value.title"
            :editMode="editMode"
        />
        <String
            label="SummaryContent"
            v-model="value.summaryContent"
            :editMode="editMode"
        />
        <String
            label="Summary"
            v-model="value.summary"
            :editMode="editMode"
        />
        <String
            label="Category"
            v-model="value.category"
            :editMode="editMode"
        />
        <String
            label="Imagepath"
            v-model="value.imagepath"
            :editMode="editMode"
        />
        <String
            label="Contents"
            v-model="value.contents"
            :editMode="editMode"
        />
        <String
            label="Status"
            v-model="value.status"
            :editMode="editMode"
        />
        <Number
            label="Price"
            v-model="value.price"
            :editMode="editMode"
        />
        <v-row class="ma-0 pa-0">
            <v-spacer></v-spacer>
            <v-btn width="64px" color="primary" @click="save">
                저장
            </v-btn>
        </v-row>
    </div>
</template>


<script>
import BaseEntity from './base-ui/BaseEntity.vue'

export default {
    name: 'BookPublication',
    mixins:[BaseEntity],
    components:{
    },
    
    data: () => ({
        path: "bookPublications",
        value: {
        }
    }),
    created(){
    },
    computed:{
    },
    methods: {
    },
}
</script>
